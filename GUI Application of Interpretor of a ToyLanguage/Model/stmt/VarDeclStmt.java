package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.Type;
import Model.value.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type typ;

    public VarDeclStmt(Type typ, String name) {
        this.name = name;
        this.typ = typ;
    }

    /*
    Function inserts into the table a new value with an 'Unknown Value'
    Input: state - PrgState
    Output: state - PrgState
     */
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value>  symTbl = state.getSymTable();
        if(symTbl.isDefined(name)) {
            throw new MyException("Variable " + name + "is already defined!");
        }
        else {
            symTbl.update(name, typ.defaultValue());
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, typ);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(typ, name);
    }

    @Override
    public String toString() {
        return typ.toString() + " " + name + ";";
    }
}
