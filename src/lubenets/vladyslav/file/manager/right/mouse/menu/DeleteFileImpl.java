package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class DeleteFileImpl implements DeleteFile {

    public void deleteThis() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);
        int response = JOptionPane.showConfirmDialog(GUICreatorImpl.jFrm, "Remove objects?", "Delete?", JOptionPane.YES_NO_OPTION);
        switch (response) {
        case JOptionPane.YES_OPTION: {
            DeleteFiles df = new DeleteFilesImpl();
            df.removeFiles(source);
            GUICreatorImpl.fl.fillData(GUICreatorImpl.path);
        }
            break;
        case JOptionPane.NO_OPTION:
            break;
        }
    }

}
