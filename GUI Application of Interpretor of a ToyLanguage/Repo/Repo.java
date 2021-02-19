package Repo;
import Model.PrgState;
import Model.stmt.IStmt;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Repo implements IRepo {

    IStmt baseProgram;
    PrgState currentProgram;
    List<PrgState> myPrgStates;
    String fileName;
    String error;

    public Repo(PrgState programState, String fileName) {
        myPrgStates = new LinkedList<>();
        baseProgram = programState.getOriginalProgram();
        currentProgram = programState;
        this.fileName = fileName;
        this.error = "";
        myPrgStates.add(programState);
    }



    @Override
    public void logPrgStateExec(PrgState prgState){
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(prgState.toString() + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return myPrgStates;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        myPrgStates = newList;
    }

    @Override
    public boolean errorThrown() {
        return !error.equals("");
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public void addError(String error) {
        this.error += error + '\n';
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public IStmt getBaseProgram() {
        return baseProgram;
    }

}
