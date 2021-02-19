package Model.dto;

import javafx.beans.property.SimpleStringProperty;

public class LockEntry {

    private SimpleStringProperty address;
    private SimpleStringProperty value;
    private Integer originalAddress;
    private Integer originalValue;

    public LockEntry(Integer address, Integer value) {
        this.address = new SimpleStringProperty(String.valueOf(address));
        this.value = new SimpleStringProperty(value.toString());
        this.originalAddress = address;
        this.originalValue = value;
    }

    public Integer getAddress() {
        return originalAddress;
    }

    public Integer getValue() {
        return originalValue;
    }
}
