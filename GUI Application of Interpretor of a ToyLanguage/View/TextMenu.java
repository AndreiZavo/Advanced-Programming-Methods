package View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    Map<String, Command> commands;

    public TextMenu() { this.commands = new HashMap<>(); }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu(){
        for(Command command : commands.values()){
            String line = String.format("%s\n%s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public ArrayList<String> getCommandsDescription(){
        ArrayList<String> textMenu = new ArrayList<>();
        for(Command command : commands.values()){
            textMenu.add(command.getDescription());
        }
        return textMenu;
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if(command == null){
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }
    }

}
