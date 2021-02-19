package Repo;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.MyList;
import Model.stmt.IStmt;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    void logPrgStateExec(PrgState prgState) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    boolean errorThrown();
    String getError();
    void addError(String error);
    IStmt getBaseProgram();

}
