package lubenets.vladyslav.file.manager.data.loader;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import lubenets.vladyslav.file.manager.main.FileModel;

/**
 * The <code>FillList</code> class represents module which will fill a list-like object by the data.
 * 
 * @author vladla
 * @author Vlad Lubenets
 */

public class FillList {


    public void fillData(FileModel fileModel) {

        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        File fileType = new File(fileModel.path);
        File[] newFileList = fileType.listFiles();
        for (int k = 0; k < newFileList.length; k++) {
            if (newFileList[k].isFile()) {
                files.add(newFileList[k].getName());
            } else
                folders.add(newFileList[k].getName());
        }

        Iterator<String> iteratorForFolders = folders.iterator();
        Iterator<String> iteratorForFiles = files.iterator();

        fileModel.modelOfTheList.clear();
        fileModel.modelOfTheList.put(0, "..");

        for (int i = 0; i < folders.size(); i++) {
            if (iteratorForFolders.hasNext()) {
                fileModel.modelOfTheList.put(i, iteratorForFolders.next());
            }
        }

        for (int i = folders.size(); i < folders.size()+files.size(); i++) {
            if (iteratorForFiles.hasNext()) {
                fileModel.modelOfTheList.put(i, iteratorForFiles.next());
            }
        }
        
    }

}
