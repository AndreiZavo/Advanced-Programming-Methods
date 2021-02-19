package Model.adt;

import Exceptions.MyException;

import java.util.Iterator;
import java.util.List;

public interface IStack<T> {

    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    String toString();
    List<T> toList();

}

