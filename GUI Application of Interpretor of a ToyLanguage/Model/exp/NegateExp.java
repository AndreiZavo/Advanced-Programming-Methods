package Model.exp;

import Exceptions.MyException;
import Exceptions.MyExecutionException;
import Exceptions.MyTypeException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.BoolType;
import Model.type.Type;
import Model.value.BoolValue;
import Model.value.Value;
import com.sun.jdi.BooleanValue;

public class NegateExp implements Exp{

    Exp expression;

    public NegateExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException {
        Value val = expression.eval(symTable, heap);
        if(val.getType().equals(new BoolType())){
            BoolValue booleanValue = (BoolValue) val;
            return new BoolValue(!booleanValue.getValue());
        }
        throw new MyExecutionException("Value is not a bool");
    }


    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type expressionType = expression.typeCheck(typeEnv);
        if(expressionType.equals(new BoolType())){
            return new BoolType();
        }
        throw new MyTypeException("Expression is not bool");
    }

    @Override
    public Exp deepcopy() {
        return new NegateExp(expression.deepcopy());
    }

    @Override
    public String toString() {
        return "not " + expression.toString();
    }
}
