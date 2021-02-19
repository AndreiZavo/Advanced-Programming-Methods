package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyExecutionException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILock;
import Model.adt.IStack;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class NewLock implements IStmt{

    String id;

    public NewLock(String id) {
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        IDict<String, Value> symblTable = state.getSymTable();
        ILock<Integer> lockTable = state.getLockTable();

        if(symblTable.isDefined(id)){
            Value value = symblTable.lookup(id);
            if(value.getType().equals(new IntType())){
                int addr = lockTable.allocate(-1);
                symblTable.update(id, new IntValue(addr));
            }
            else{
                throw new MyExecutionException("Variable not an int");
            }
        }
        else{
            throw new MyExecutionException("Variable is not defined");
        }
        state.setExeStack(stk);
        state.setSymTable(symblTable);
        state.setLockTable(lockTable);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(id);
        if(varType.equals(new IntType())){
            return typeEnv;
        }
        throw new MyTypeException("Var is not an int");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLock(id);
    }

    @Override
    public String toString() {
        return "newLock (" + id + ")";
    }
}
