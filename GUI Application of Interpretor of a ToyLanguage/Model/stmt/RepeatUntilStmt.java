package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.NegateExp;
import Model.type.BoolType;
import Model.type.Type;

import java.util.Map;

public class RepeatUntilStmt implements IStmt{

    Exp condition;
    IStmt statement;

    public RepeatUntilStmt(Exp condition, IStmt statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(new CompStmt(
                statement,
                new WhileStmt(
                    new NegateExp(condition),
                    statement
                )
        ));
        state.setExeStack(stk);
        return null;
    }


    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type conditionType = condition.typeCheck(typeEnv);
        if(conditionType.equals(new BoolType())){
            statement.typeCheck(typeEnv);
            return typeEnv;
        }
        throw new MyTypeException("Condition is not bool");
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(condition, statement);
    }

    @Override
    public String toString() {
        return "repeat {\n" + statement.toString() + "} until (" + condition.toString() + ")";
    }
}
