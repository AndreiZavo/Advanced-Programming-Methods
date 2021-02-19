package Model.adt;
import Exceptions.MyADTException;
import Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<>();
    }

    // Function adds a new key-value pair
    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    // Function updates a value associated with a given key
    @Override
    public void update(T1 v1, T2 v2){
        dictionary.put(v1, v2);
    }

    // Function returns the object(if it's defined) that has the value parametrised
    @Override
    public T2 lookup(T1 id) throws MyException{
        try{
            return dictionary.get(id);
        }catch (Exception error) {
            throw new MyADTException("ID not defined yet\n");
        }
    }

    // Function checks if a value exists in the dictionary
    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    //Function returns a string display of the map
    @Override
    public String toString(){
        StringBuilder content = new StringBuilder();

        for (Map.Entry<T1, T2> entry : dictionary.entrySet()) {
            content.append("(")
                    .append(entry.getKey())
                    .append("->")
                    .append(entry.getValue())
                    .append(")")
                    .append(", ");
        }
        return content.toString();
    }

    @Override
    public void remove(T1 v1) throws MyException {
        if(!dictionary.containsKey(v1)){
            throw new MyException("Key doesn't exist for removal");
        }
        dictionary.remove(v1);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(Map<T1, T2> newMap) {
        dictionary = newMap;
    }

    @Override
    public IDict<T1, T2> deepcopy() {
        HashMap<T1, T2> newMap = new HashMap<>(dictionary);
        Dict<T1, T2> newDict = new Dict<>();
        newDict.setContent(newMap);
        return newDict;
    }

    // Function returns a set of the keys from dictionary
    @Override
    public Set<T1> keySet() {
        return dictionary.keySet();
    }
}
