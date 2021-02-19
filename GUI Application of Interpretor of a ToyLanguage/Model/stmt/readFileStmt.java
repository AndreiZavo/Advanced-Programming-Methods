package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.MyHeap;
import Model.exp.Exp;
import Model.type.IntType;
import Model.type.StringType;
import Model.type.Type;
import Model.value.IntValue;
import Model.value.StringValue;
import Model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFileStmt implements IStmt{
    public Exp exp;
    public String var_name;

    public readFileStmt(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, Value> symTable = state.getSymTable();
        IDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeap<Value> heap = state.getHeap();
        Value exp_value = exp.eval(symTable, heap);
        if(exp_value.getType().equals(new StringType())){
            StringValue string_val = (StringValue) exp_value;
            if(fileTable.isDefined(string_val)){
                BufferedReader bufferedReader = fileTable.lookup(string_val);
                try{
                    String line = bufferedReader.readLine();
                    Value int_val;
                    IntType type = new IntType();
                    if(line == null){
                        int_val = type.defaultValue();
                    }
                    else{
                        int_val = new IntValue(Integer.parseInt(line));
                    }
                    symTable.update(var_name, int_val);
                }
                catch (IOException e) {
                    throw new MyException(e.getMessage());
                }
            }
            else{
                throw new MyException("String value is not defined in the file table");
            }
        }
        else{
            throw new MyException("Type of value is not a String");
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws MyException {
        Type typVar = typeEnv.lookup(var_name);
        Type typExp = exp.typeCheck(typeEnv);
        if(typVar.equals(new IntType())){
            if(typExp.equals(new StringType())){
                return typeEnv;
            }
            else{
                throw new MyException("Expression is not an Integer");
            }
        }
        else{
            throw new MyException("Variable name is not a String");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "readF(" + var_name + " " +exp.toString() + ");";
    }
}
