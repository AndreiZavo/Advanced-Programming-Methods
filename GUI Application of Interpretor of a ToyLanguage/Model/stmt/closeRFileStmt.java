package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.StringType;
import Model.type.Type;
import Model.value.StringValue;
import Model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFileStmt implements IStmt{
    public Exp exp;

    public closeRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTable = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        if(val.getType().equals(new StringType())){
            IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue string_val = (StringValue) val;
            if(fileTable.isDefined(string_val)){
                BufferedReader bufferedReader = fileTable.lookup(string_val);
                try{
                    bufferedReader.close();
                }
                catch (IOException e) {
                    throw new MyException(e.getMessage());
                }
                fileTable.remove(string_val);
            }
            else{
                throw new MyException("String value is not defined in the file table");
            }
        }
        else{
            throw new MyException("Value is not a String type");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typExp = exp.typeCheck(typeEnv);
        if(typExp.equals(new StringType())){
            return typeEnv;
        }
        else{
            throw new MyException("The expression for closing the file is not String");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ");";
    }
}
