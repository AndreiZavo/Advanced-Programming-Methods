package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.Type;
import Model.value.BoolValue;
import Model.value.Value;

public class WhileStmt implements IStmt{

    Exp exp;
    IStmt statement;

    public WhileStmt(Exp exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> myStack  = state.getExeStack();
        IDict<String, Value> symTable = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        if(val.getType().equals(new BoolType())){
            BoolValue bool_val = (BoolValue) val;
            if(bool_val.getValue()){
                myStack.push(this);
                myStack.push(statement);
            }
        }
        else{
            throw new MyException("While condition is not boolean");
        }

        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typeCheck(typeEnv);
        if(typExp.equals(new BoolType())){
            statement.typeCheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else{
            throw new MyException("The condition of WHILE has not the type bool");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "while (" + exp + "){\t" + statement + "}";
    }


}
