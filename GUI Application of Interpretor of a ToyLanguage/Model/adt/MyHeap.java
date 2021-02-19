package Model.adt;

import Model.value.Value;

import java.util.Map;
import java.util.Set;

public class MyHeap<V> extends Dict<Integer, V>{

    int freeLocation = 1;

    public MyHeap() { super(); }

    public int getFreeLocation() { return freeLocation; }

    void updateFreeLocation() { freeLocation++; }

    public void add_content(V value){
        updateFreeLocation();
        super.update(freeLocation, value);
    }


    public void setContent(Map<Integer, V> content){
        this.dictionary = content;
    }

}
