package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.RelationExp;
import Model.type.Type;

import java.util.Map;

public class SwitchStmt implements IStmt{

    Exp condition;
    Exp case1;
    IStmt stmt1;
    Exp case2;
    IStmt stmt2;
    IStmt defaultStmt;

    public SwitchStmt(Exp condition, Exp case1, IStmt stmt1, Exp case2, IStmt stmt2, IStmt defaultStmt) {
        this.condition = condition;
        this.case1 = case1;
        this.stmt1 = stmt1;
        this.case2 = case2;
        this.stmt2 = stmt2;
        this.defaultStmt = defaultStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(new IfStmt(
                new RelationExp(
                        condition,
                        case1,
                        3
                ),
                stmt1,
                new IfStmt(
                        new RelationExp(
                                condition,
                                case2,
                                3
                        ),
                        stmt2,
                        defaultStmt
                )
        ));
        return null;
    }

    private static IDict<String, Type> clone(IDict<String, Type> table){
        IDict<String, Type> newSymbolTable = new Dict<>();
        for (Map.Entry<String, Type> entry: table.getContent().entrySet()) {
            newSymbolTable.add(entry.getKey(), entry.getValue());
        }
        return newSymbolTable;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type conditionType = condition.typeCheck(typeEnv);
        Type case1Type = case1.typeCheck(typeEnv);
        Type case2Type = case2.typeCheck(typeEnv);
        if (conditionType.equals(case1Type) && conditionType.equals(case2Type)) {
            stmt1.typeCheck(clone(typeEnv));
            stmt2.typeCheck(clone(typeEnv));
            defaultStmt.typeCheck(clone(typeEnv));
            return typeEnv;
        }
        throw new MyTypeException("Condition and cases not of same type");
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(condition, case1, stmt1, case2, stmt2, defaultStmt);
    }

    @Override
    public String toString() {
        return "switch (" + condition.toString() + ") ( case " + case1.toString() + ": " + stmt1.toString() +
                ") ( case " + case2.toString() + ": " + stmt2.toString() + ") default: " + defaultStmt.toString() + ")";
    }
}
