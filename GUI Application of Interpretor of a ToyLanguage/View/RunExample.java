package View;
import Controller.Controller;
import Exceptions.MyException;

public class RunExample extends Command{

    private final Controller ctr;

    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try{
            ctr.allStep();
        }
        catch (MyException | InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public Controller getController() {
        return ctr;
    }
}
