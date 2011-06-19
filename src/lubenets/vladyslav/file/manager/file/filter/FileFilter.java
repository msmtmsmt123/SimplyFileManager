package lubenets.vladyslav.file.manager.file.filter;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lubenets.vladyslav.file.manager.data.loader.FillList;
import lubenets.vladyslav.file.manager.main.FileModel;

public class FileFilter {
    Map<Integer, String> listModel;

    public Map<Integer, String> filterThis(String filterPattern, String path, FileModel fileModel) {
        String regexp = "";
        String input = filterPattern;
        listModel = new HashMap<Integer, String>();

        if (filterPattern==null) {
            return null;
        }
        
        if (input.equals(".")) {
            input = "\\.";
        }
        regexp = input;
        Pattern p = Pattern.compile(regexp);

        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        if (input.length() != 0) {
            int searchPosition;
            File fileType = new File(path);
            File[] newFileList = fileType.listFiles();
            for (int k = 0; k < newFileList.length; k++) {

                if (newFileList[k].isFile()) {
                    Matcher m = p.matcher(newFileList[k].getAbsolutePath());
                    if (m.find()) {
                        System.out.println(m.start());
                        if (!path.equals(File.separator)) {
                            searchPosition = path.length() + 1;
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
                        if (!path.equals(File.separator)) {
                            searchPosition = path.length() + 1;
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
            listModel.clear();
            listModel.put(0, "..");
//            GUICreatorImpl.lm.clear();
//            GUICreatorImpl.lm.addElement("..");

        } else {
            FillList fl = new FillList();
            fl.fillData(path, fileModel);
        }

        Iterator<String> iteratorForFolders = folders.iterator();
        Iterator<String> iteratorForFiles = files.iterator();

        for (int i = 0; i < folders.size(); i++) {
            if (iteratorForFolders.hasNext()) {
                listModel.put(i, iteratorForFolders.next());
            }
        }

        for (int i = 0; i < files.size(); i++) {
            if (iteratorForFiles.hasNext()) {
                listModel.put(i, iteratorForFiles.next());
            }
        }

        return listModel;

    }

}
