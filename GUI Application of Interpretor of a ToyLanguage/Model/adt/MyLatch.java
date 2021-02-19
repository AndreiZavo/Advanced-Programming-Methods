package Model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLatch<T> implements ILatch<T> {

    AtomicInteger freeValue;
    Map<Integer, T> latchTable;

    public MyLatch() {
        this.freeValue = new AtomicInteger(0);
        this.latchTable = new HashMap<>();
    }

    @Override
    public synchronized int allocate(T value) {
        latchTable.put(freeValue.incrementAndGet(), value);
        return freeValue.get();
    }

    @Override
    public synchronized void update(int address, T value) {
        latchTable.put(address, value);
    }

    @Override
    public synchronized Map<Integer, T> getContent() {
        return latchTable;
    }

    @Override
    public synchronized boolean exists(int address) {
        return latchTable.containsKey(address);
    }

    @Override
    public synchronized void setContent(Map<Integer, T> map) {
        latchTable = map;
    }

    @Override
    public T get(int addr) {
        return latchTable.get(addr);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem : latchTable.keySet()){
            if(elem != null){
                s.append(elem.toString()).append(" -> ").append(latchTable.get(elem).toString()).append('\n');
            }
        }
        return s.toString();
    }
}
