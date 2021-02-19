package Model.value;

import Model.type.IntType;
import Model.type.Type;

public class IntValue implements Value{
    int val;

    public IntValue(int val){ this.val = val; }

    public IntValue() { this.val = 0; }

    public int getValue() { return val; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        IntValue auxiliaryIntValue = (IntValue) o;
        return val == auxiliaryIntValue.val;
    }

    @Override
    public String toString() {return Integer.toString(val); }

    @Override
    public Type getType() { return new IntType(); }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }
}
