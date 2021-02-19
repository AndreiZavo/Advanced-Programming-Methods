package Model.value;

import Model.type.StringType;
import Model.type.Type;

import java.util.Objects;

public class StringValue implements Value{
    String val;

    public StringValue(String val) { this.val = val; }

    public StringValue() { this.val = ""; }

    public String getValue() { return val; }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue auxiliaryStringValue = (StringValue) o;
        return Objects.equals(val, auxiliaryStringValue.getValue());
    }

    @Override
    public String toString() { return val; }

    @Override
    public Type getType() { return new StringType(); }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }
}
