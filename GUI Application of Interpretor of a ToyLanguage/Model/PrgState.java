package Model;
import Exceptions.MyException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.StringValue;
import Model.value.Value;

import java.io.BufferedReader;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, Value> symTable;
    IList<Value> out;
    IDict<StringValue, BufferedReader> fileTable;
    MyHeap<Value> heap;
    IStmt originalProgram;
    ILock<Integer> lockTable;
    ILatch<Integer> latchTable;
    public int stateID;
    public static int lastID = 0;


    public PrgState(IStack<IStmt> stk, IDict<String, Value> symTable,
                    IDict<StringValue, BufferedReader> fileTable, MyHeap<Value> heap, IList<Value> ot, IStmt prg, ILock<Integer> lockTable, ILatch<Integer> latchTable){
        exeStack = stk;
        this.symTable = symTable;
        this.fileTable = fileTable;
        this.heap = heap;
        out = ot;
        this.lockTable = lockTable;
        this.latchTable = latchTable;
        originalProgram = prg.deepCopy();
        this.stateID = getProgramStateID();
        stk.push(prg);
    }

    public static PrgState createEmptyProgramState(IStmt program) {
        return new PrgState(new MyStack<>(),
                new Dict<>(),
                new Dict<>(),
                new MyHeap<>(),
                new MyList<>(),
                program,
                new MyLock<>(),
                new MyLatch<>()
        );
    }

    public int getStateID() {
        return stateID;
    }

    public static synchronized int getProgramStateID() {
        ++ lastID;
        return lastID;
    }

    public boolean isNotCompleted() { return !exeStack.isEmpty(); }

    public MyHeap<Value> getHeap() {
        return heap;
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public IDict<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDict<String, Value> symTable) {
        this.symTable = symTable;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {return fileTable;}

    public ILatch<Integer> getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(ILatch<Integer> latchTable) {
        this.latchTable = latchTable;
    }

    public IList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public ILock<Integer> getLockTable() {
        return lockTable;
    }

    public void setLockTable(ILock<Integer> lockTable) {
        this.lockTable = lockTable;
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("Program stack is empty");
        IStmt currentState = exeStack.pop();
        return currentState.execute(this);
    }

    public String toString(){
        String prgContent = "--------PROGRAM STATE---------";
        prgContent += "\nID: " + stateID +
                "\nHeap: \n" + heap.toString() +
                "\nStack: \n" + exeStack.toString() +
                "\nSymbol Table: \n" + symTable.toString() +
                "\nFile Table: \n" + fileTable.toString() +
                "\nLock Table: \n" + lockTable.toString() +
                "\nLatch Table: \n" + latchTable.toString() +
                "\nOut list: \n" + out.toString() + '\n';
        return prgContent;
    }
}