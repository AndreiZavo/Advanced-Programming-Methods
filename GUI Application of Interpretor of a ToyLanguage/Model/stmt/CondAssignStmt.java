package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.Type;

public class CondAssignStmt implements IStmt{

    String id;
    Exp expression1;
    Exp expression2;
    Exp expression3;

    public CondAssignStmt(String id, Exp expression1, Exp expression2, Exp expression3) {
        this.id = id;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(new IfStmt(expression1, new AssignStmt(id, expression2), new AssignStmt(id, expression3)));
        state.setExeStack(stk);
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type conditionType = expression1.typeCheck(typeEnv);
        if(conditionType.equals(new BoolType())){
            Type variableType = typeEnv.lookup(id);
            Type then = expression2.typeCheck(typeEnv);
            Type elseType = expression3.typeCheck(typeEnv);
            if( variableType.equals(then) && variableType.equals(elseType)){
                return typeEnv;
            }
            throw new MyTypeException("Variable and expression do not have the same type");
        }
        throw new MyTypeException("Condition not of type bool");
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignStmt(id, expression1, expression2, expression3);
    }

    @Override
    public String toString() {
        return "id = " + expression1.toString() + " ? " + expression2.toString() + " : " + expression3.toString();
    }
}
