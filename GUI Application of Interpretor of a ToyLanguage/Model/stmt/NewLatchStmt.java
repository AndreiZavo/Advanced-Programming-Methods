package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class NewLatchStmt implements IStmt{

    String id;
    Exp expression;

    public NewLatchStmt(String id, Exp expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value expressionResult = expression.eval(state.getSymTable(), state.getHeap());
        if(expressionResult.getType().equals(new IntType())){
            IntValue num1 = (IntValue) expressionResult;
            if(state.getSymTable().isDefined(id)){
                Value varValue = state.getSymTable().lookup(id);
                if(varValue.getType().equals(new IntType())){
                    int addr = state.getLatchTable().allocate(num1.getValue());
                    state.getSymTable().update(id, new IntValue());
                }
            }
            else{
                throw  new MyException("Var not defined");
            }
        }
        else{
            throw new MyException("Expression not of type int");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type varType = typeEnv.lookup(id);
        Type expressionType = expression.typeCheck(typeEnv);
        if(varType.equals(new IntType()) && expressionType.equals(new IntType())){
            return typeEnv;
        }
        throw new MyTypeException("Latch must be of type int");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(id, expression);
    }

    @Override
    public String toString() {
        return "newLatch (" + id + ", " + expression.toString() + ")";
    }
}
