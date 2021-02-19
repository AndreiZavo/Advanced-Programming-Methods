package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyExecutionException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.Type;
import Model.value.Value;

public class AssignStmt implements IStmt{
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    /*
    Function inserts/updates a value of an object in the symbol table
    Input: state - PrgState
    Output: state - PrgState
     */
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTbl = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        if (symTbl.isDefined(id)) {                     // check if the id is defined in the table
            Value val = exp.eval(symTbl, heap);               // create a Value object by evaluating the table
            Type typId = (symTbl.lookup(id).getType()); // create a Type object with the type of the id
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new MyExecutionException("Declared type of variable " +
                        id + " and type of the assigned expression do not match\n");
        }
        else
            throw new MyException("The used variable " + id +
                    " was not declared before");
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(id);
        Type typExp = exp.typeCheck(typeEnv);
        if(typVar.equals(typExp)){
            return typeEnv;
        }
        else{
            throw new MyException("Assigment: right hand side and left side have different types");
        }

    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp);
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString() + ";";
    }
}
