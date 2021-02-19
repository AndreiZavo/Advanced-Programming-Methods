package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.*;
import Model.type.Type;
import Model.value.Value;

import java.util.Map;

public class ForkStmt implements IStmt{

    IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        var out = state.getOut();
        var fileTable = state.getFileTable();
        var lockTable = state.getLockTable();
        var latchTable = state.getLatchTable();

        IStack<IStmt> newStack = new MyStack<>();
        IDict<String, Value> newSymTable = new Dict<>();
        for(Map.Entry<String, Value> entry : symTable.getContent().entrySet()) {
            newSymTable.update(entry.getKey(), entry.getValue().deepCopy());
        }
        return new PrgState(newStack, newSymTable, fileTable, heap, out, statement, lockTable, latchTable);
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        statement.typeCheck(typeEnv.deepcopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ");";
    }
}
