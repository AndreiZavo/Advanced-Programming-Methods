package Model.stmt;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.type.Type;

public class CompStmt implements IStmt{
    IStmt first, second;

    public CompStmt(IStmt first, IStmt second){
        this.first = first;
        this.second = second;
    }

    // Function adds to the stack the two statements
    @Override
    public PrgState execute(PrgState state){
        IStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first, second);
    }

    @Override
    public String toString(){
        return  first.toString() + "\n" + second.toString() + '\n';
    }
}
