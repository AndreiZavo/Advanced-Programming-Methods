package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class AwaitStmt implements IStmt{

    String id;

    public AwaitStmt(String id) {
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(state.getSymTable().isDefined(id)){
            Value foundIndex = state.getSymTable().lookup(id);
            if(foundIndex.getType().equals(new IntType())){
                int found = ((IntValue) foundIndex).getValue();
                if(state.getLatchTable().exists(found)){
                    if(state.getLatchTable().get(found) != 0){
                        state.getExeStack().push(this);
                    }
                }
                else{
                    throw new MyException("Latch doe not exist");
                }
            }
            else{
                throw new MyException("Var not an int");
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
        throw new MyTypeException("Var not an int");
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(id);
    }

    @Override
    public String toString() {
        return "await (" + id + ")";
    }

}
