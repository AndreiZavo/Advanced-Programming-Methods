package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.BoolType;
import Model.type.IntType;
import Model.type.Type;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.Value;

public class RelationExp implements Exp{
    Exp exp1, exp2;
    int rel;

    public RelationExp(Exp exp1, Exp exp2, int rel) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.rel = rel;
    }

    public Exp getExp1() {
        return exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public int getRel() {
        return rel;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException {
        Value val1, val2;
        val1 = exp1.eval(symTable, heap);     // takes Value object from the table
        if (val1.getType().equals(new IntType())){
            val2 = exp2.eval(symTable, heap);
            if(val2.getType().equals(new IntType())){
                IntValue int_val1 = (IntValue) val1;    //create a new IntValue object by casting
                IntValue int_val2 = (IntValue) val2;    // the Value from table
                int int1 = int_val1.getValue();
                int int2 = int_val2.getValue();
                switch (rel){                           //return BoolValue depending on rel value
                    case 1:
                        return new BoolValue(int1 < int2);
                    case 2:
                        return new BoolValue(int1 <= int2);
                    case 3:
                        return new BoolValue(int1 == int2);
                    case 4:
                        return new BoolValue(int1 != int2);
                    case 5:
                        return new BoolValue(int1 > int2);
                    case 6:
                        return new BoolValue(int1 >= int2);
                    default:
                        throw new MyException("Operand is not valid");
                }
            }
            else{
                throw new MyException("Second operand is not an integer");
            }
        }
        else{
            throw new MyException("First operand is not an integer");
        }
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = exp1.typeCheck(typeEnv);
        typ2 = exp2.typeCheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new BoolType();
            }
            else{
                throw new MyException("Second operand is not a Boolean");
            }
        }
        else{
            throw new MyException("First operand is not Boolean");
        }
    }


    @Override
    public Exp deepcopy() {
        return new RelationExp(exp1.deepcopy(), exp2.deepcopy(), rel);
    }

    @Override
    public String toString() {
        String relOp = "";
        switch (rel) {
            case 1:
                relOp = "<";
                break;
            case 2:
                relOp = "<=";
                break;
            case 3:
                relOp = "==";
                break;
            case 4:
                relOp = "!=";
                break;
            case 5:
                relOp = ">";
                break;
            case 6:
                relOp = ">=";
                break;
            default:
                relOp = "";
                break;
        }
        return exp1.toString() + " " + relOp + " " + exp2.toString();
    }
}
