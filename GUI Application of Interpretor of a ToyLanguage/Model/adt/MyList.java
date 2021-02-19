package Model.adt;

import java.util.*;

public class MyList<T> implements IList<T> {
    private final List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }


    // Function adds element to queue
    @Override
    public void add(T v) { list.add(v); }

    @Override
    public Iterator<T> iterator() { return list.iterator(); }

    // Function returns a string display of the list
    @Override
    public String toString(){
        StringBuilder content = new StringBuilder("[");
        for(T item: list){
            content.append(item).append("|");
        }
        return content + "]";
    }

    // Function returns the size of the list
    @Override
    public int size() {
        return list.size();
    }

    // Function returns the element from position i
    @Override
    public T get(int i) {
        return list.get(i);
    }

}
