package Model.value;

import Model.type.BoolType;
import Model.type.Type;

import java.util.Objects;

public class BoolValue implements Value{
    boolean val;

    public BoolValue(boolean val){ this.val = val; }

    public BoolValue() { this.val = false; }

    public boolean getValue() {return val;}

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue auxiliaryBoolValue = (BoolValue) o;
        return val == auxiliaryBoolValue.getValue();
    }

    @Override
    public String toString() {return String.valueOf(val);}

    @Override
    public Type getType() { return new BoolType(); }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
