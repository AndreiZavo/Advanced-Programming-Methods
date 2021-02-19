package Model.adt;
import Exceptions.MyADTException;
import Exceptions.MyException;

import java.util.*;

public class MyStack<T> implements IStack<T> {
    private Stack<T> stk;

    public MyStack(){
        this.stk = new Stack<>();
    }

    // Function removes and returns the element from the top
    @Override
    public T pop() throws MyException {
        if(stk.size() == 0){
            throw new MyADTException("Stack is empty");
        }
        return stk.pop();
    }

    // Function adds a new element to the stack
    @Override
    public void push(T v) {
        stk.push(v);
    }

    // Function checks if the stack is empty
    @Override
    public boolean isEmpty() {
        return stk.isEmpty();
    }

    // Function: returns a string display of the stack
    @Override
    public String toString(){
        StringBuilder content = new StringBuilder();
        for(T item : stk){
            content.append(item).append("|");
        }
        return content.toString();
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>(stk);
        Collections.reverse(list);
        return list;
    }


}
