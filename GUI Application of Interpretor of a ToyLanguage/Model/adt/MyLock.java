package Model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLock<T> implements ILock<T>{

    AtomicInteger freeValue;
    Map<Integer, T> lockTable;

    public MyLock() {
        freeValue = new AtomicInteger();
        lockTable = new HashMap<>();
    }

    public MyLock(AtomicInteger freeValue, Map<Integer, T> lockTable) {
        this.freeValue = freeValue;
        this.lockTable = lockTable;
    }

    @Override
    public synchronized int allocate(T value) {
        lockTable.put(freeValue.incrementAndGet(), value);
        return freeValue.get();
    }

    @Override
    public synchronized void update(int address, T value) {
        lockTable.put(address, value);
    }

    @Override
    public synchronized Map<Integer, T> getContent() {
        return lockTable;
    }

    @Override
    public synchronized boolean exists(int address) {
        return lockTable.containsKey(address);
    }

    @Override
    public synchronized void setContent(Map<Integer, T> map) {
        lockTable = map;
    }

    @Override
    public synchronized T get(int addr) {
        return lockTable.get(addr);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem : lockTable.keySet()){
            if(elem != null){
                s.append(elem.toString()).append(" -> ").append(lockTable.toString()).append('\n');
            }
        }
        return s.toString();
    }
}
