package Model.type;

import Model.value.BoolValue;
import Model.value.Value;

public class BoolType implements Type{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }

    @Override
    public String toString() {
        return "Bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue();
    }

    @Override
    public Type deepcopy() {
        return new BoolType();
    }
}
