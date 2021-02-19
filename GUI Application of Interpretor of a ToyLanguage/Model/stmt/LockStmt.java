package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILock;
import Model.adt.IStack;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class LockStmt implements IStmt{

    String id;

    public LockStmt(String id) {
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
                IntValue foundIndex = (IntValue) symblTable.lookup(id);
                if(!lockTable.exists(foundIndex.getValue())){
                    throw new MyException("Lock does not exist");
                }
                else{
                    if(lockTable.get(foundIndex.getValue()) == -1){
                        lockTable.update(foundIndex.getValue(), state.getStateID());
                    }
                    else{
                        stk.push(this);
                    }
                }
            }
            else{
                throw new MyException("Var not an int");
            }
        }
        else{
            throw new MyException("Var not defined");
        }
        state.setLockTable(lockTable);
        state.setSymTable(symblTable);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(id);
        if(varType.equals(new IntType())){
            return typeEnv;
        }
        throw new MyTypeException("Var not an int");
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(id);
    }

    @Override
    public String toString() {
        return "lock (" + id + ")";
    }
}
