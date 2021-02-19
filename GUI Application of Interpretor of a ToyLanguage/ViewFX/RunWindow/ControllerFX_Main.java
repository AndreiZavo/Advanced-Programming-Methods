package ViewFX.RunWindow;

import Controller.Controller;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.*;
import Model.dto.HeapEntry;
import Model.dto.LockEntry;
import Model.dto.SymTableEntry;
import Model.stmt.IStmt;
import Model.value.StringValue;
import Model.value.Value;
import View.Command;
import View.Interpreter;
import View.TextMenu;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class ControllerFX_Main implements Initializable {

    private Controller controller = null;
    private PrgState lastMainProgram;

    public ControllerFX_Main(Controller controller) {
        this.controller = controller;
        this.lastMainProgram = null;
    }

    @FXML
    TextField noPrgIDs;

    @FXML
    Button stepByStepButton;

    @FXML
    ListView<Integer> programIdListView;

    @FXML
    ListView<IStmt> executionStackListView;

    @FXML
    ListView<StringValue> fileTableListView;

    @FXML
    ListView<Value> outListView;

    @FXML
    TableView<SymTableEntry> symbolTableView;

    @FXML
    TableView<HeapEntry> heapTableView;

    @FXML
    private TableColumn<HeapEntry, String> heapAddress, heapValue;

    @FXML
    private TableColumn<SymTableEntry, String> symTableName, symTableValue;

    @FXML
    private TableView<LockEntry> lockTableView;

    @FXML
    private TableColumn<SymTableEntry, String> lockAddressTableColumn, lockIdentifierTableColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noPrgIDs.setDisable(true);
        heapAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        lockAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        lockIdentifierTableColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        programIdListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            populateWindows();
        } catch (MyException e) {
            AlertBox.display("Error", "Nothing to execute!");
        }
    }

    private void populateWindows() throws MyException {
        List<PrgState> states = controller.getRepository().getPrgList();
        PrgState currentPrg;
        if (states.size() == 0) {
            noPrgIDs.setText("#0 ProgramStates");
            if (lastMainProgram == null) {
                throw new MyException("empty");
            }
            else {
                currentPrg = lastMainProgram;
                lastMainProgram = null;
            }
        } else {
            lastMainProgram = states.get(0);
            currentPrg = states.get(0);
            noPrgIDs.setText("#" + states.size() + " PrgStates");
        }
        populateProgramListView(states);
        programIdListView.getSelectionModel().selectIndices(0);
        populateExecutionStackListView(currentPrg);
        populateFileTableListView(currentPrg);
        populateOutListView(currentPrg);
        populateHeapTableView(currentPrg);
        populateSymTableView(currentPrg);
        populateLockTableView(currentPrg);
    }

    // Populate ListViews
    private void populateProgramListView(List<PrgState> states) {
        List<Integer> stateIDs = states.stream().map(PrgState::getStateID).collect(Collectors.toList());
        programIdListView.setItems(FXCollections.observableArrayList(stateIDs));

    }

    private void populateExecutionStackListView(PrgState prgState) {
        IStack<IStmt> stk = prgState.getExeStack();
        executionStackListView.setItems(FXCollections.observableArrayList(stk.toList()));
    }

    private void populateFileTableListView(PrgState prgState) {
        IDict<StringValue, BufferedReader> fileTable = prgState.getFileTable();
        List<StringValue> list = new ArrayList<>(fileTable.getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(list));

    }

    private void populateOutListView(PrgState prgState) {
        IList<Value> out = prgState.getOut();
        ArrayList<Value> outList = new ArrayList<>();
        for (int i = 0; i < out.size(); ++i) {
            outList.add(out.get(i));
        }
        outListView.setItems(FXCollections.observableArrayList(outList));
    }

    // Populate TableViews
    private void populateHeapTableView(PrgState prgState) {
        MyHeap<Value> heap = prgState.getHeap();
        ArrayList<HeapEntry> entries = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heap.getContent().entrySet()) {
            entries.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableArrayList(entries));

    }

    private void populateLockTableView(PrgState prgState){
        ILock<Integer> lock = prgState.getLockTable();
        ArrayList<LockEntry> entries = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : lock.getContent().entrySet()) {
            entries.add(new LockEntry(entry.getKey(), entry.getValue()));
        }
        lockTableView.setItems(FXCollections.observableArrayList(entries));
    }

    private void populateSymTableView(PrgState prgState) {
        IDict<String, Value> symTable = prgState.getSymTable();
        ArrayList<SymTableEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Value> entry : symTable.getContent().entrySet()) {
            entries.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }
        symbolTableView.setItems(FXCollections.observableArrayList(entries));
    }

    public void executeStepByStep(){
        List<PrgState> states = null;
        try {
            states = controller.getRepository().getPrgList();
            if (states.size() > 0 || lastMainProgram != null)
                controller.evaluateOnlyOneStep();
        } catch (InterruptedException | MyException e) {
            AlertBox.display("Error", "The program is finished");
        }
        try {
            populateWindows();
        } catch (MyException e) {
            AlertBox.display("Error", "There is nothing to do");
        }
    }

    public void switchProgramState(javafx.scene.input.MouseEvent mouseEvent) {
        List<PrgState> states = controller.getRepository().getPrgList();
        int index = programIdListView.getSelectionModel().getSelectedIndex();
        PrgState program = states.get(index);

        populateExecutionStackListView(program);
        populateSymTableView(program);
    }


}
