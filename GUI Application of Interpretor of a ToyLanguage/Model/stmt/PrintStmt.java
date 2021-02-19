package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.exp.VarExp;
import Model.type.Type;
import Model.value.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ");";
    }

    /*
    Function adds to the out list a print statement
    Input: state - PrgState
    Output: state - PrgState
     */
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTbl = state.getSymTable();
        IList<Value> out = state.getOut();
        MyHeap<Value> heap = state.getHeap();
        out.add(exp.eval(symTbl, heap));
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp);
    }
}
