package Model.stmt;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.Type;

public class NopStmt implements IStmt{

    public NopStmt() {}

    // Function returns the state
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
         return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "Nop";
    }
}
