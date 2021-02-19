package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.type.Type;
import Model.value.Value;

public interface Exp {
    Value eval(IDict<String, Value> symTable, MyHeap<Value> heap) throws MyException;
    Type typeCheck(IDict<String, Type> typeEnv) throws MyException;
    Exp deepcopy();
    }
