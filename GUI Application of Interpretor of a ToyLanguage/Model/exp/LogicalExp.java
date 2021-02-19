package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.BoolType;
import Model.type.Type;
import Model.value.BoolValue;
import Model.value.Value;

public class LogicalExp implements Exp{
    Exp e1, e2;
    int op;

    public LogicalExp(Exp e1, Exp e2, int op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    /*
    Function: evaluates the values from the symbol table
            and unites them into an logical operation accordingly
    Input: symTable - Dict<String, Value>
    Output: IntValue
     */
    @Override
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException{
        Value val1 = e1.eval(symTable, heap);             // takes Value object from the symbol table
        if(val1.getType().equals(new BoolType())){  // check for type
            Value val2 = e2.eval(symTable, heap);
            if(val2.getType().equals(new BoolType())){
                BoolValue bool_value1 = (BoolValue) val1;   // create new BoolValue object by
                BoolValue bool_value2 = (BoolValue) val2;   // casting the value from table
                boolean b1, b2;
                b1 = bool_value1.getValue();
                b2 = bool_value2.getValue();
                switch (op){
                    case 1:
                        return new BoolValue(b1 && b2);
                    case 2:
                        return new BoolValue(b1 || b2);
                    default:
                        return new BoolValue();
                }
            }
            else
                throw new MyException("Second operand is not a boolean");
        }
        else
            throw new MyException("First operand is not a boolean");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if(typ1.equals(new BoolType())){
            if(typ2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new MyException("Second operand is not Boolean");
            }
        }
        else{
            throw new MyException("First operand is not Boolean");
        }
    }


    @Override
    public Exp deepcopy() {
        return new LogicalExp(e1.deepcopy(), e2.deepcopy(), op);
    }

    @Override
    public String toString(){
        String opStr = ((op == 1) ? "and" : "or");
        return e1 + opStr + e2;
    }
}
