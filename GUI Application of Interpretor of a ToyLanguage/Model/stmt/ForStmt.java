package Model.stmt;

import Exceptions.MyException;
import Exceptions.MyTypeException;
import Model.PrgState;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.RelationExp;
import Model.exp.VarExp;
import Model.type.IntType;
import Model.type.Type;

import java.util.Map;

public class ForStmt implements IStmt{
    Exp expression1;
    Exp expression2;
    Exp expression3;
    IStmt statement;

    public ForStmt(Exp expression1, Exp expression2, Exp expression3, IStmt statement) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        stk.push(new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", expression1),
                        new WhileStmt(
                                new RelationExp(
                                        new VarExp("v"),
                                        expression2,
                                        1
                                ),
                                new CompStmt(
                                        statement,
                                        new AssignStmt("v", expression3))
                        )
                )
        ));
        return null;
    }

    private static IDict<String, Type> clone(IDict<String, Type> table){
        IDict<String, Type> newSymblTable = new Dict<>();
        for(Map.Entry<String, Type> entry : table.getContent().entrySet()){
            newSymblTable.add(entry.getKey(), entry.getValue());
        }
        return newSymblTable;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        IDict<String, Type> table1 = new VarDeclStmt(new IntType(), "v").typeCheck(clone(typeEnv));
        Type vType = table1.lookup("v");
        Type expression1Type = expression1.typeCheck(table1);
        Type expression2Type = expression2.typeCheck(table1);
        Type expression3Type = expression3.typeCheck(table1);
        if(vType.equals(new IntType())){
            if(expression1Type.equals(new IntType())){
                if(expression2Type.equals(new IntType())){
                    if(expression3Type.equals(new IntType())){
                        statement.typeCheck(clone(table1));
                        return typeEnv;
                    }
                    throw new MyTypeException("Expression 3 is not int");
                }
                throw new MyTypeException("Expression 2 is not int");
            }
            throw new MyTypeException("Expression 1 is not int");
        }
        throw new MyTypeException("V is not an int");

    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(expression1, expression2, expression3, statement);
    }

    @Override
    public String toString() {
        return "for(v = " + expression1.toString() + "; v < " + expression2.toString() +
                "; v = " + expression3.toString() + ") {\n\t" + statement.toString() + " }";
    }
}
