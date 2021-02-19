package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILock;
import Model.adt.IStack;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class UnlockStmt implements IStmt{

    String id;

    public UnlockStmt(String id) {
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
                    if(lockTable.get(foundIndex.getValue()).equals(state.getStateID())){
                        lockTable.update(foundIndex.getValue(), -1);
                    }
                }
            }
            else{
                throw new MyException("Var is not an int");
            }
        }
        else{
            throw new MyException("Var is not defined");
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
        throw new MyException("Var not of type int");
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(id);
    }

    @Override
    public String toString() {
        return "unlock( " + id + ")";
    }
}
