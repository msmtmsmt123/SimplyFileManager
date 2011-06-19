package lubenets.vladyslav.file.manager.main;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class FileModel extends ApplicationModel {

    public Map<Integer, String> modelOfTheList = new HashMap<Integer, String>();
    public String path;
    protected int selectedIndex;
    public String buffer;
    public boolean filesMustMove;

    public FileModel(Application application) {
        super(application);
    }

    public void firstRun() {
        File[] filesRoots = File.listRoots();
        for (int i = 0; i < filesRoots.length; i++) {
            getApplication().getFileModel().modelOfTheList.put(i, filesRoots[i].getPath());
        }

    }

    public void displayFilesFromAPath() {
        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        if (getApplication().getFileModel().path != null) {
            File newPath = new File(getApplication().getFileModel().path);
            String[] filesList = newPath.list();
            getApplication().getFileModel().clearListModel();

            for (int i = 0; i < filesList.length; i++) {

                File isItFile = new File(getApplication().getFileModel().path + File.separator + filesList[i]);

                if (isItFile.isFile()) {
                    files.add(filesList[i]);
                }
                if (isItFile.isDirectory()) {
                    folders.add(filesList[i]);
                }
            }

            Iterator<String> iteratorForFolders = folders.iterator();
            Iterator<String> iteratorForFiles = files.iterator();

            getApplication().getFileModel().modelOfTheList.put(0, "..");
            for (int i = 0; i < folders.size(); i++) {
                if (iteratorForFolders.hasNext()) {
                    getApplication().getFileModel().modelOfTheList.put(i + 1, iteratorForFolders.next());
                }
            }

            for (int i = 0; i < getApplication().getFileModel().modelOfTheList.size() + files.size(); i++) {
                if (iteratorForFiles.hasNext()) {
                    getApplication().getFileModel().modelOfTheList.put(getApplication().getFileModel().modelOfTheList.size() + i, iteratorForFiles.next());
                }
            }

        }
        getApplication().getViewModel().setListModel(getApplication().getFileModel().modelOfTheList);
    }

    private void clearListModel() {
        modelOfTheList.clear();
    }

    // Will work for *NIX only
    void setPath() {

        File forAnalysis = new File(getApplication().getFileModel().path + File.separator + getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex));

        File[] rootsList = File.listRoots();

        for (int i = 0; i < rootsList.length; i++) {
            if (getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex).toString().equals(rootsList[i].toString())) {
                forAnalysis = new File(getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex));
            }
        }

        if (forAnalysis.toString().endsWith("..")) {
            int decPosition = getApplication().getFileModel().path.lastIndexOf(File.separator);
            if (decPosition == 0) {
                getApplication().getFileModel().path = File.separator;
                return;
            } else
                getApplication().getFileModel().path = getApplication().getFileModel().path.substring(0, decPosition);
            return;
        }

        if (forAnalysis.isFile()) {
        }
        if (forAnalysis.isDirectory()) {
            if (getApplication().getFileModel().path == null) {
                getApplication().getFileModel().path = getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
            } else {

                for (int i = 0; i < rootsList.length; i++) {
                    if (getApplication().getFileModel().path.equals(rootsList[i].toString())) {
                        getApplication().getFileModel().path = getApplication().getFileModel().path + getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
                    } else
                        getApplication().getFileModel().path = getApplication().getFileModel().path + File.separatorChar + getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);

                }

            }
        }

    }

}
