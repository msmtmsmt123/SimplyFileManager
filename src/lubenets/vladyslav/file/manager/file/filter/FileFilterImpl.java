package lubenets.vladyslav.file.manager.file.filter;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class FileFilterImpl implements FileFilter {

    public void filterThis() {
        String regexp = "";
        String input = "";

        input = GUICreatorImpl.jFilter.getText();
        if (input.equals(".")) {
            input = "\\.";
        }
        regexp = input;
        Pattern p = Pattern.compile(regexp);

        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        if (input.length() != 0) {
            int searchPosition;
            File fileType = new File(GUICreatorImpl.path);
            File[] newFileList = fileType.listFiles();
            for (int k = 0; k < newFileList.length; k++) {

                if (newFileList[k].isFile()) {
                    Matcher m = p.matcher(newFileList[k].getAbsolutePath());
                    if (m.find()) {
                        System.out.println(m.start());
                        if (!GUICreatorImpl.path.equals(File.separator)) {
                            searchPosition = GUICreatorImpl.path.length() + 1;
                        } else if (input.equals(".")) {
                            searchPosition = 0;
                        } else
                            searchPosition = 1;
                        if (m.start() == searchPosition) {
                            files.add(newFileList[k].getName());
                        }
                    }
                } else {
                    Matcher m = p.matcher(newFileList[k].getAbsolutePath());
                    if (m.find()) {
                        System.out.println(m.start());
                        if (!GUICreatorImpl.path.equals(File.separator)) {
                            searchPosition = GUICreatorImpl.path.length() + 1;
                        } else if (input.equals(".")) {
                            searchPosition = 0;
                        } else
                            searchPosition = 1;
                        if (m.start() == searchPosition) {
                            folders.add(newFileList[k].getName());
                        }
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
