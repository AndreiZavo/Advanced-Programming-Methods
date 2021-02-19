package Model.exp;
import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.Type;
import Model.value.StringValue;
import Model.value.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String id){ this.id = id; }

    // Function return the object associated with the given id from the table
    @Override
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException {
        return symTable.lookup(id);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public Exp deepcopy() {
        return new VarExp(new String(id));
    }

    public String toString() {return id;}

}
