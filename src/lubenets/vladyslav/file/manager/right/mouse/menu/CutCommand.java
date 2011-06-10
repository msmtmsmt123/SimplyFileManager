package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class CutCommand extends Command {

    public CutCommand() {
        strCommand = GUICreatorImpl.CUT;
    }

    public void execute() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);
        GUICreatorImpl.buffer = source.getAbsolutePath();
        filesMustMove = true;

    }

}
