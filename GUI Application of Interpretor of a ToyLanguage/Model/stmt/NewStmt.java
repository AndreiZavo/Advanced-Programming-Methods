package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyExecutionException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.RefType;
import Model.type.Type;
import Model.value.RefValue;
import Model.value.Value;

public class NewStmt implements IStmt{
    String var_name;
    Exp exp;

    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTbl = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        if(symTbl.isDefined(var_name)){
            if(symTbl.lookup(var_name).getType() instanceof RefType){
                Value exp_val = exp.eval(symTbl, heap);
                if(symTbl.lookup(var_name).getType().equals(new RefType(exp_val.getType()))){
                    heap.add_content(exp_val);
                    symTbl.update(var_name, new RefValue(heap.getFreeLocation(), exp_val.getType()));
                }
                else{
                    throw new MyException("Reference does not point to expected type");
                }
            }
            else{
                throw new MyExecutionException("The type of the variable is not RefType");
            }
        }
        else{
            throw new MyExecutionException("Variable is not defined in the symbol table");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typExp = exp.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typExp))){
            return typeEnv;
        }
        else{
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
        }
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + exp.toString() + ");";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
