package Model.exp;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.Type;
import Model.value.Value;

public class ValueExp implements Exp{
    Value e;

    public ValueExp(Value e){ this.e = e; }

    // Function gets the Value Object
    @Override
    public Value eval(IDict<String, Value> table, MyHeap<Value> heap){
        return e;
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv){
        return e.getType();
    }

    @Override
    public Exp deepcopy() {
        return new ValueExp(e.deepCopy());
    }

    public String toString() {return e.toString(); }
}
