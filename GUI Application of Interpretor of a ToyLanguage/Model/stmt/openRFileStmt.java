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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class openRFileStmt implements IStmt{
    public Exp exp;

    public openRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTable = state.getSymTable();
        MyHeap<Value> heap = state.getHeap();
        Value exp_value = exp.eval(symTable, heap);
        if(exp_value.getType().equals(new StringType())){
            IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue string_value = (StringValue) exp_value;
            if(!fileTable.isDefined(string_value)){
                try{
                    Reader reader = new FileReader(string_value.getValue());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.update(string_value, bufferedReader);
                }
                catch (FileNotFoundException e){
                    throw new MyException(e.getMessage());
                }
            }
            else{
                throw new MyException("The value is already defined");
            }
        }
        else{
            throw new MyException("The declared variable has not a String type");
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
            throw new MyException("The expression for opening the file is not String");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ");";
    }
}
