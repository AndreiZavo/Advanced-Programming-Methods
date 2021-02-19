package Model.type;

import Model.value.IntValue;
import Model.value.Value;

public class IntType implements Type{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public String toString() {
        return "Integer";
    }

    @Override
    public Value defaultValue() {
        return new IntValue();
    }

    @Override
    public Type deepcopy() {
        return new IntType();
    }
}
