package Model.adt;

import java.util.Iterator;

public interface IList<T> {
    void add(T v);
    Iterator<T> iterator();
    int size();
    T get(int i);
}
