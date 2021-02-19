package Controller;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.ILock;
import Model.adt.IStack;
import Model.adt.MyLock;
import Model.stmt.IStmt;
import Model.type.Type;
import Model.value.RefValue;
import Model.value.Value;
import Repo.IRepo;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {

    private final IRepo repository;
    private ExecutorService executor;

    public Controller(IRepo repo) {
        this.repository = repo;
        executor = Executors.newFixedThreadPool(3);
    }


    public IRepo getRepository() {
        return repository;
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTable, List<Integer> heapAddress, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTable.contains(e.getKey()) ||
                        heapAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(List<Collection<Value>> symTableValues){
        List<Integer> symTableAddresses = new ArrayList<Integer>();
        symTableValues.forEach(symbolTable -> symbolTable.stream()
                .filter(value -> value instanceof RefValue).forEach(value -> symTableAddresses.add(((RefValue) value).getAddress())));
        return symTableAddresses;
    }

    public List<Integer> getAddressFromHeap(Collection<Value> heapVal){
        return heapVal.stream()
                .filter(val -> val instanceof  RefValue)
                .map(value -> {RefValue v1 = (RefValue) value; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList
                .stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());

    }

    public void oneStepForAllPrg(List<PrgState> list_of_prg) throws InterruptedException, MyException {

        list_of_prg.forEach(prgState -> {
            try {
                repository.logPrgStateExec(prgState);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        // Run concurrently one step for each of the existing PrgState
        List<Callable<PrgState>> callList = list_of_prg.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgSList = null;
        try {
            newPrgSList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            repository.addError(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        catch(InterruptedException e){
            throw new MyException(e.getMessage());
        }
        if(repository.errorThrown()){
            throw new MyException(repository.getError());
        }

        // Add new created thread to the list of existing threads
        list_of_prg.addAll(newPrgSList);

        //Print the program state list
        list_of_prg.forEach(prgState -> {
            try {
                repository.logPrgStateExec(prgState);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        // Save the current programs in the repository
        repository.setPrgList(list_of_prg);
    }

    public void collectGarbage(List<PrgState> programs_list){
        PrgState progState = programs_list.get(0);
        var dummy = programs_list.stream()
                .map(program -> program.getSymTable().getContent().values())
                .collect(Collectors.toList());
        progState.getHeap().setContent(safeGarbageCollector(
                getAddrFromSymTable(programs_list.stream().map(program -> program.getSymTable().getContent().values()).collect(Collectors.toList())),
                getAddressFromHeap(progState.getHeap().getContent().values()),
                progState.getHeap().getContent()
        ));
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        // Remove completed programs
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while(prgList.size() > 0){
            // collect garbage
            collectGarbage(prgList);
            oneStepForAllPrg(prgList);
            // remove completed programs
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();

        // update repository state
        repository.setPrgList(prgList);

    }

    public void evaluateOnlyOneStep() throws MyException, InterruptedException {
        List<PrgState> states = removeCompletedPrg(repository.getPrgList());
        collectGarbage(states);
        oneStepForAllPrg(states);
        states = removeCompletedPrg(repository.getPrgList());
        repository.setPrgList(states);
        if(states.isEmpty()){
            executor.shutdown();
        }

    }

    public void typecheckOriginalProgram() throws MyException {
        Dict<String, Type> typeEnvironment = new Dict<>();
        IStmt originalProgram = repository.getBaseProgram();
        originalProgram.typeCheck(typeEnvironment);
    }

    public ILock<Integer> getLockTable(){
        if(repository.getPrgList().size() == 0){
            return new MyLock<>();
        }
        return repository.getPrgList().get(0).getLockTable();
    }

    @Override
    public String toString() {
        return repository.getBaseProgram().toString();
    }


}
