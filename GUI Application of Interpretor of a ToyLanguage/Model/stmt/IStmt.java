package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException;
    IStmt deepCopy();

}
