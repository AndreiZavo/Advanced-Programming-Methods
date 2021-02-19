package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.RefType;
import Model.type.Type;
import Model.value.RefValue;
import Model.value.Value;

public class ReadHeapExp implements Exp{

    Exp expression;

    public ReadHeapExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException {
        Value val = expression.eval(symTable, heap);
        if( val.getType() instanceof RefType){
            RefValue ref_val = (RefValue) val;
            if(heap.isDefined(ref_val.getAddress())){
                return heap.lookup(ref_val.getAddress());
            }
            else{
                throw new MyException("Address does not exist in the heap");
            }
        }
        else{
            throw new MyException("The expression does not have a RefType");
        }
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typ = expression.typeCheck(typeEnv);
        if(typ instanceof RefType){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else{
            throw new MyException("The rH argument is not RefType");
        }
    }

    @Override
    public Exp deepcopy() {
        return new ReadHeapExp(expression.deepcopy());
    }

    @Override
    public String toString() { return "rH(" + expression.toString() + ")"; }
}
