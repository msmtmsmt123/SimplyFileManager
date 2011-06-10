package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    private List<Command> commandList = new ArrayList<Command>();

    public Invoker() {
        commandList.add(new OpenWithCommand());
        commandList.add(new CopyCommand());
        commandList.add(new CutCommand());
        commandList.add(new PasteCommand());
        commandList.add(new RenameCommand());
        commandList.add(new DeleteCommand());
        commandList.add(new PropertiesCommand());
    }

    public Command getCommand(String strCommand) {
        for (Command obj : commandList) {
            if (obj.strCommand.equals(strCommand)) {
                return obj;
            }
        }

        return null;

    }
}
