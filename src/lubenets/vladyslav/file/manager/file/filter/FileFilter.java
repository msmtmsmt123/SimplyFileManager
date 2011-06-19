package lubenets.vladyslav.file.manager.file.filter;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import lubenets.vladyslav.file.manager.controller.GUICreatorImpl;

public class FileFilter{

    public void filterThis() {
        String input = "";

        input = GUICreatorImpl.jFilter.getText();
        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        if (input.length() != 0) {
            File fileType = new File(GUICreatorImpl.path);
            File[] newFileList = fileType.listFiles();
            for (int k = 0; k < newFileList.length; k++) {

                if (newFileList[k].isFile()) {
                    if (newFileList[k].getName().contains(input)) {
                        files.add(newFileList[k].getName());
                    }

                } else {
                    if (newFileList[k].getName().contains(input)) {
                        folders.add(newFileList[k].getName());
                    }

                }

            }
            GUICreatorImpl.lm.clear();
            GUICreatorImpl.lm.addElement("..");

        } else {
            GUICreatorImpl.fl.fillData(GUICreatorImpl.path);
        }

        Iterator<String> iteratorForFolders = folders.iterator();
        Iterator<String> iteratorForFiles = files.iterator();

        for (int i = 0; i < folders.size(); i++) {
            if (iteratorForFolders.hasNext()) {
                GUICreatorImpl.lm.addElement(iteratorForFolders.next());
            }
        }

        for (int i = 0; i < files.size(); i++) {
            if (iteratorForFiles.hasNext()) {
                GUICreatorImpl.lm.addElement(iteratorForFiles.next());
            }
        }

    }

}