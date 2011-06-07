package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class RenameFileImpl implements RenameFile {

    public void renameThis() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);
        File response = new File(GUICreatorImpl.path + File.separator
                + JOptionPane.showInputDialog("Enter a new file name"));
        if (!source.renameTo(response)) {
            JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "I can`t rename this!");
        }
        GUICreatorImpl.fl.fillData(GUICreatorImpl.path);

    }

}
