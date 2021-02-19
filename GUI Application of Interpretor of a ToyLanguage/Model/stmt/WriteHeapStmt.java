package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.RefType;
import Model.type.StringType;
import Model.type.Type;
import Model.value.RefValue;
import Model.value.Value;


public class WriteHeapStmt implements IStmt{

    String var_name;
    Exp expression;

    public WriteHeapStmt(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTable = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        if(symTable.isDefined(var_name)){
            if(symTable.lookup(var_name).getType() instanceof RefType){
                RefValue ref_val  = (RefValue) symTable.lookup(var_name);
                if(heap.isDefined(ref_val.getAddress())) {
                    Value exp_val = expression.eval(symTable, heap);
                    if (symTable.lookup(var_name).getType().equals(new RefType(exp_val.getType()))) {
                        int address = ref_val.getAddress();
                        heap.update(address, exp_val);
                    }
                    else {
                        throw new MyException("The given expression have a different type then to what is it pointing");
                    }
                }
                else{
                    throw new MyException(var_name +  "'s address is not pointing to a heap");
                }
            }
            else{
                throw new MyException("The type of the variable is not RefType");
            }
        }
        else{
            throw new MyException("The variable is not defined in the table");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(var_name);
        Type typExp = expression.typeCheck(typeEnv);
        if(typVar instanceof RefType){
            if(typVar.equals(new RefType(typExp))){
                return typeEnv;
            }
            else{
                throw new MyException("Write Heap stmt: right hand side and left hand side have different types");
            }
        }
        else{
            throw new MyException("Variable name is not a String");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(var_name, expression.deepcopy());
    }

    @Override
    public String toString() {
        return "wH(" + var_name + ", " + expression.toString() + ")";
    }
}
