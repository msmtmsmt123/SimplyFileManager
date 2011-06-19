package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    private List<Cmd> commandList = new ArrayList<Cmd>();

    public Invoker() {
        commandList.add(new OpenWithCommand());
        commandList.add(new CopyCommand());
        commandList.add(new CutCommand());
        commandList.add(new PasteCommand());
        commandList.add(new RenameCommand());
        commandList.add(new DeleteCommand());
        commandList.add(new PropertiesCommand());
    }

    public Cmd getCommand(String strCommand) {
        for (Cmd obj : commandList) {
            if (obj.strCommand.equals(strCommand)) {
                return obj;
            }
        }

        return null;

    }
}
