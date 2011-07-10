package lubenets.vladyslav.file.manager.main;

import java.awt.Dimension;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Controller extends ApplicationModel {
    public boolean filterActivated;

    public Controller(Application application) {
        super(application);
        getApplication().getFileModel().firstRun();
        getApplication().getViewModel().setListModel(getApplication().getFileModel().modelOfTheList);
    }

    public void setDataToListModelAfterSelection(DefaultListModel lm) {


            getApplication().getFileModel().setPath();
            getApplication().getFileModel().displayFilesFromAPath();
            getApplication().getViewModel().jFrm.setTitle(getApplication().getFileModel().path);

    }

    public void showDialog(String string) {
        JOptionPane.showMessageDialog(getApplication().getViewModel().jFrm, string);
    }

    public String showID(String string, String lastCommandForFileOpenning) {
        if(lastCommandForFileOpenning!=null) {
            return JOptionPane.showInputDialog(string, lastCommandForFileOpenning);
        } else {
            return JOptionPane.showInputDialog(string);
        }
        
    }

    public String askForDelete() {
        int response = JOptionPane.showConfirmDialog(getApplication().getViewModel().jFrm, "Remove objects?", "Delete?", JOptionPane.YES_NO_OPTION);
        switch (response) {
        case JOptionPane.YES_OPTION: {
            return "yes";
        }
        case JOptionPane.NO_OPTION: {
            return "no";
        }
        }
        return null;
    }

//Properties command    
    public void showProperties(String[] infoForList) {

        JList propertiesList;
        JScrollPane scrollPaneForList;
        propertiesList = new JList(infoForList);
        scrollPaneForList = new JScrollPane(propertiesList);
        scrollPaneForList.setPreferredSize(new Dimension(120, 70));

        JFrame frameForProperties = new JFrame("Properties");
        frameForProperties.setSize(600, 150);

        frameForProperties.getContentPane().add(scrollPaneForList);
        frameForProperties.setVisible(true);

    }

    public void setPopMenu() {

        File forAnalysis = new File(getApplication().getFileModel().path + File.separator + getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex));

        File[] rootsList = File.listRoots();

        for (int i = 0; i < rootsList.length; i++) {
            if (getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex).toString().equals(rootsList[i].toString())) {
                forAnalysis = new File(getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex));
            }
        }
        
        if (getApplication().getController().filterActivated) {
            forAnalysis = new File(getApplication().getFileModel().path + File.separator + getApplication().getViewModel().lm.get(getApplication().getFileModel().selectedIndex));            
            getApplication().getController().filterActivated = false;
        }

//        getApplication().getViewModel().lm.clear();
        
//        if (forAnalysis.toString().endsWith("..")) {
//            int decPosition = getApplication().getFileModel().path.lastIndexOf(File.separator);
//            if (decPosition == 0) {
//                getApplication().getFileModel().path = File.separator;
//                return;
//            } else
//                getApplication().getFileModel().path = getApplication().getFileModel().path.substring(0, decPosition);
//            return;
//        }

        if (forAnalysis.isFile()) {
            getApplication().getViewModel().pom.showOpenWithForFolder(getApplication().getViewModel());
            return;
        }

        if (forAnalysis.isDirectory()) {
            getApplication().getViewModel().pom.hideOpenWithForFolder(getApplication().getViewModel());
        }        
    }
}
