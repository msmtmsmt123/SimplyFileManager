package lubenets.vladyslav.file.manager.data.loader;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListModel;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;
import lubenets.vladyslav.file.manager.kerner.ListForModel;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileModule;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileModuleImpl;

public class FillListImpl implements FillList {

    public void fillData() {

        final ListForModel modelList = new ListForModel();

        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        int idx = GUICreatorImpl.jList.getSelectedIndex();

        if (idx == -1) {
            return;
        }

        Object value = GUICreatorImpl.jList.getSelectedValues()[0];
        String fullPath = GUICreatorImpl.path + File.separator + value.toString();
        if (value.toString().equals("..")) {
            fullPath = value.toString();
        }

        if (!fullPath.equals("..")) {
            GUICreatorImpl.backStep = (String) fullPath;
        }

        GUICreatorImpl.fileList = GUICreatorImpl.fm.createFileList((String) fullPath);

        if (idx == 0) {
            if (!fullPath.equals(File.separator)) {
                int decPosition = GUICreatorImpl.backStep.lastIndexOf(File.separator);
                if (decPosition == 0) {
                    GUICreatorImpl.backStep = GUICreatorImpl.backStep.substring(0, decPosition + 1);
                } else
                    GUICreatorImpl.backStep = GUICreatorImpl.backStep.substring(0, decPosition);

                GUICreatorImpl.fileList = GUICreatorImpl.fm.createFileList(GUICreatorImpl.backStep);
            }
        }

        DefaultListModel lm = (DefaultListModel) GUICreatorImpl.jList.getModel();

        if (GUICreatorImpl.fileList != null) {
            lm.clear();
            lm.addElement("..");
        }

        if (GUICreatorImpl.fileList != null) {

            for (int i = 0; i < GUICreatorImpl.fileList.length; i++) {
                modelList.putElement(GUICreatorImpl.fileList[i].getAbsoluteFile());
            }

            for (int j = 0; j < modelList.getSize(); j++) {
                String analysis = modelList.getElementAt(j).toString();
                int indexNumber = analysis.indexOf("text=");
                String afterAnalysis = analysis.substring(indexNumber + 5, analysis.length() - 1);

                GUICreatorImpl.path = afterAnalysis.substring(0, afterAnalysis.lastIndexOf(File.separator));

                if (afterAnalysis.lastIndexOf(File.separator) == 0) {
                    GUICreatorImpl.path = File.separator;
                }

                GUICreatorImpl.jFrm.setTitle(GUICreatorImpl.path);

                String output = afterAnalysis.substring(afterAnalysis.lastIndexOf(File.separator) + 1, afterAnalysis.length());

                File fileType = new File(GUICreatorImpl.path + File.separator + output);
                if (fileType.isFile()) {
                    files.add(output);
                } else
                    folders.add(output);

            }

            Iterator<String> iteratorForFolders = folders.iterator();
            Iterator<String> iteratorForFiles = files.iterator();

            for (int i = 0; i < folders.size(); i++) {
                if (iteratorForFolders.hasNext()) {
                    lm.addElement(iteratorForFolders.next());
                }
            }

            for (int i = 0; i < files.size(); i++) {
                if (iteratorForFiles.hasNext()) {
                    lm.addElement(iteratorForFiles.next());
                }
            }
        }

        if (GUICreatorImpl.fileList == null) {

            String fileToOpen = GUICreatorImpl.path + File.separator + value;

            OpenFileModule openFile = new OpenFileModuleImpl();
            openFile.openThis(GUICreatorImpl.fad, fileToOpen, value);

        }

    }

    public void fillData(String newPath) {

        SortedSet<String> folders = new TreeSet<String>();
        SortedSet<String> files = new TreeSet<String>();

        File fileType = new File(newPath);
        File[] newFileList = fileType.listFiles();
        for (int k = 0; k < newFileList.length; k++) {
            if (newFileList[k].isFile()) {
                files.add(newFileList[k].getName());
            } else
                folders.add(newFileList[k].getName());
        }

        Iterator<String> iteratorForFolders = folders.iterator();
        Iterator<String> iteratorForFiles = files.iterator();

        GUICreatorImpl.lm.clear();
        GUICreatorImpl.lm.addElement("..");

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
