package Model.exp;
import Exceptions.MyException;
import Exceptions.MyExpressionException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.IntType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.Value;

public class ArithExp implements Exp{
    char op;
    Exp e1, e2;

    public ArithExp(char op, Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    /*
    Function: evaluates the values from the symbol table
            and unites them into an arithmetic operation accordingly
    Input: symTable - Dict<String, Value>
    Output: IntValue
     */
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException {
        Value val1, val2;
        val1 = e1.eval(symTable, heap);   // takes the Value object from the table
        if (val1.getType().equals(new IntType())){  //checks if type of Value object is IntType
            val2 = e2.eval(symTable, heap);
            if(val2.getType().equals(new IntType())) {
                IntValue int_val1 = (IntValue) val1;    // create a new IntValue object by casting
                IntValue int_val2 = (IntValue) val2;    // the Value from table
                int int1 = int_val1.getValue();     // takes the integer value associated with the value
                int int2 = int_val2.getValue();
                switch (op) {
                    case '+':
                        return new IntValue(int1 + int2);
                    case '-':
                        return new IntValue(int1 - int2);
                    case '*':
                        return new IntValue(int1 * int2);
                    case '/':
                        if (int2 == 0) throw new MyExpressionException("division by 0");
                        else return new IntValue(int1 / int2);
                    default:
                        return new IntValue();
                }
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new IntType();
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
    public Exp deepcopy() {
        return new ArithExp(op, e1.deepcopy(), e2.deepcopy());
    }

    public char getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
