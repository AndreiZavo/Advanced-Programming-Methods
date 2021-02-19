package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class CountDownStmt implements IStmt{

    String id;

    public CountDownStmt(String id) {
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(state.getSymTable().isDefined(id)){
            Value foundIndex = state.getSymTable().lookup(id);
            if(foundIndex.getType().equals(new IntType())){
                int found = ((IntValue) foundIndex).getValue();
                if(state.getLatchTable().exists(found)){
                    if(state.getLatchTable().get(found) > 0){
                        state.getLatchTable().update(found, state.getLatchTable().get(found) - 1);
                    }
                    state.getOut().add(new IntValue(state.getStateID()));
                }
                else{
                    throw new MyException("Latch does not exist");
                }
            }
            else{
                throw new MyException("Var is not an int");
            }
        }
        else{
            throw new MyException("Var not defined");
        }
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
        return new CountDownStmt(id);
    }

    @Override
    public String toString() {
        return "countDown (" + id + ")";
    }
}
