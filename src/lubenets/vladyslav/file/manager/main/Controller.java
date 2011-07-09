package lubenets.vladyslav.file.manager.main;

import java.awt.Dimension;

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
}
