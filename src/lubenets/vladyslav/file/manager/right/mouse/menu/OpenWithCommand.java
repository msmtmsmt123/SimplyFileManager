package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.controller.GUICreatorImpl;
import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;

public class OpenWithCommand extends Command {

    public OpenWithCommand() {
        strCommand = GUICreatorImpl.ACTION_OPEN_WITH;
    }
    
    public void execute() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();

        File fileType = new File(GUICreatorImpl.path + File.separator + value);
        if (fileType.isFile()) {
            String fileToOpen = GUICreatorImpl.path + File.separator + value;
            openThis(GUICreatorImpl.fad, fileToOpen, value);
        } else
            GUICreatorImpl.fl.fillData();

    }


    public void openThis(FileAssosiationDetecter fad, String fileToOpen, Object value) {
        Runtime r = Runtime.getRuntime();
        String response;

        String fileType = value.toString().substring(value.toString().lastIndexOf('.') + 1, value.toString().length());

        String lastCommandForFileOpenning = fad.getLastCommand(fileType);

        if (lastCommandForFileOpenning == null) {
            response = JOptionPane.showInputDialog("Enter a program name to open file");
        } else
            response = lastCommandForFileOpenning;

        if (response == null || response.length() == 0) {
            return;
        }

        fad.setLastCommand(fileType, response);

        String parameters[] = { response, fileToOpen };

        try {
            r.exec(parameters);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Error to run a programm!");
            return;
        }
        
        GUICreatorImpl.rs.writeToFile(fad);


    }

}
