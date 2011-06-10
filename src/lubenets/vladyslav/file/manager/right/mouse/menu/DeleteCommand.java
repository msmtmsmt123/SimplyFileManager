package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class DeleteCommand extends Command {

    public DeleteCommand() {
        strCommand = GUICreatorImpl.DELETE;
    }

    public void execute() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);
        int response = JOptionPane.showConfirmDialog(GUICreatorImpl.jFrm, "Remove objects?", "Delete?", JOptionPane.YES_NO_OPTION);
        switch (response) {
        case JOptionPane.YES_OPTION: {
            removeFiles(source);
            GUICreatorImpl.fl.fillData(GUICreatorImpl.path);
        }
            break;
        case JOptionPane.NO_OPTION:
            break;
        }

    }

    public void removeFiles(File source) {
        File[] fileList = source.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    removeFiles(fileList[i]);
                    if (!fileList[i].delete()) {
                        JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not delete a file!");
                    }
                } else {
                    if (!fileList[i].delete()) {
                        JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not delete a file!");
                    }

                }
            }
        }
        if (!source.delete()) {
            JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not create a directory!");
        }

    }

}
