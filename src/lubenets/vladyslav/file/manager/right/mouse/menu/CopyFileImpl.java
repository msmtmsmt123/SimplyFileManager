package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class CopyFileImpl implements CopyFile {

    public void copyThis() {
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);
        GUICreatorImpl.buffer = source.getAbsolutePath();
        GUICreatorImpl.filesMustMove = false;

    }

}
