package View;

import Controller.Controller;

public class ExitCommand extends Command{

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public Controller getController() {
        return null;
    }
}
