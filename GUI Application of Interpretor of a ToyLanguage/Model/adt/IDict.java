package Model.adt;
import Exceptions.MyADTException;
import Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2) throws MyADTException;
    T2 lookup(T1 id) throws MyException;
    boolean isDefined(T1 id);
    String toString();
    public void remove(T1 v1) throws MyException;
    public Map<T1, T2> getContent();
    public void setContent(Map<T1, T2> newMap);
    public IDict<T1, T2> deepcopy();
    Set<T1> keySet();

}
