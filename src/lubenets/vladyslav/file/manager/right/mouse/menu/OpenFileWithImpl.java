package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class OpenFileWithImpl implements OpenFileWith {

    public void openThis() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();

        File fileType = new File(GUICreatorImpl.path + File.separator + value);
        if (fileType.isFile()) {
            String fileToOpen = GUICreatorImpl.path + File.separator + value;

            OpenFileModule openFile = new OpenFileModuleImpl();
            openFile.openThis(GUICreatorImpl.fad, fileToOpen, value);
        } else
            GUICreatorImpl.fl.fillData();

    }

}
