package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyExpressionException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.Type;
import Model.value.BoolValue;
import Model.value.Value;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS, elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    /*
    Function: evaluates an if statement and return
              the state with the corresponding instruction
    Input: state - PrgState
    Output: state - PrgState
     */
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getExeStack();
        IDict<String, Value> symTbl = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        Value exp_value = exp.eval(symTbl, heap);
        try{
            boolean bool_value = ((BoolValue) exp_value).getValue(); // create a boolean object according to the type of the exp_value
            if(bool_value)
                stack.push(thenS);
            else
                stack.push(elseS);
        }catch (Exception error_message){
            throw new MyExpressionException("Expression is not boolean\n");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            thenS.typeCheck(typeEnv.deepcopy());
            elseS.typeCheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else{
            throw new MyException("The condition of IF has not the type bool");
        }
    }

    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then {" +
                thenS.toString() + "} else {" +
                elseS.toString() + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepcopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
