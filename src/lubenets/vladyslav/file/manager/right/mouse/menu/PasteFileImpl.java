package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class PasteFileImpl implements PasteFile {

    public void pasteThis() {
        File destination;
        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        if (value == null || (value.equals(".."))) {
            destination = new File(GUICreatorImpl.path);
        } else
            destination = new File(GUICreatorImpl.path + File.separator + value);

        CopyFiles cf = new CopyFilesImpl();
        cf.copingFiles(GUICreatorImpl.buffer, destination.getAbsolutePath());
        if (GUICreatorImpl.filesMustMove) {
            File toDelete = new File(GUICreatorImpl.buffer);
            DeleteFiles df = new DeleteFilesImpl();
            df.removeFiles(toDelete);
        }
        GUICreatorImpl.fl.fillData();

    }

}
