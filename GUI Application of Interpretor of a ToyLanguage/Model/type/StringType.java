package Model.type;

import Model.value.StringValue;
import Model.value.Value;

public class StringType implements Type{

    @Override
    public boolean equals(Object obj) { return obj instanceof StringType; }

    @Override
    public String toString() { return "String"; }

    @Override
    public Value defaultValue() { return new StringValue(); }

    @Override
    public Type deepcopy() {
        return new StringType();
    }
}
