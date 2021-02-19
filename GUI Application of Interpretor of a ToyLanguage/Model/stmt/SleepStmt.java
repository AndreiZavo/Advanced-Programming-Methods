package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.ArithExp;
import Model.exp.Exp;
import Model.exp.ValueExp;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class SleepStmt implements IStmt{

    Exp expression;

    public SleepStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        if(!value.equals(new IntValue(0))){
            stk.push(new SleepStmt(new ArithExp('-', expression, new ValueExp(new IntValue(1)))));
        }
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if(expressionType.equals(new IntType())){
            return typeEnv;
        }
        throw new MyTypeException("Sleep must have an int as parameter");
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(expression);
    }

    @Override
    public String toString() {
        return "sleep (" + expression.toString() + ")";
    }
    
}
