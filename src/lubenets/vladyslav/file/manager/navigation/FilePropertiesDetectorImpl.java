package lubenets.vladyslav.file.manager.navigation;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.FilePropertiesDetector;

public class FilePropertiesDetectorImpl implements FilePropertiesDetector {

    public void showProperties() {
        JList propertiesList;
        JScrollPane scrollPaneForList;

        String file = null;
        String fileSize = null;
        String parent;
        String hidden;
        String dateOfTheLastModified;
        String writeReadPermission;

        String value = (String) GUICreatorImpl.jList.getSelectedValue();
        File source = new File(GUICreatorImpl.path + File.separator + value);

        if (source.isDirectory()) {
            file = "Directory" + source;
            fileSize = "Folder size is " + source.length() + " byte(s)";
        } else {
            file = "Unknown file type";
            fileSize = "Unknuown size";
        }

        if (source.isFile()) {
            file = "File" + source;
            fileSize = "File size is " + source.length() + " byte(s)";
        } else {
            file = "Unknown file type";
            fileSize = "Unknuown";
        }

        if (source.canWrite()) {
            if (source.canRead())
                writeReadPermission = source.getPath()
                        + " is read-write";
            else
                writeReadPermission = source.getPath()
                        + " is write only";
        } else {
            if (source.canRead())
                writeReadPermission = source.getPath()
                        + " is read only";
            else
                writeReadPermission = "Permission to "
                        + source.getPath() + " denied";
        }

        if (source.getParent() == null) {
            parent = source.getPath() + " is a root directory.";
        } else {
            parent = "Parent of " + source.getPath() + " is "
                    + source.getParent() + ".";
        }

        if (source.isHidden()) {
            hidden = source.getPath() + " is Hidden.";
        } else
            hidden = source.getPath() + " is not Hidden.";

        dateOfTheLastModified = source.getPath()
                + " was last modified on "
                + new java.util.Date(source.lastModified());

        String[] infoForList = { file, fileSize, parent, hidden,
                dateOfTheLastModified, writeReadPermission };

        propertiesList = new JList(infoForList);
        scrollPaneForList = new JScrollPane(propertiesList);
        scrollPaneForList.setPreferredSize(new Dimension(120, 70));

        JFrame frameForProperties = new JFrame("Properties");
        frameForProperties.setSize(600, 150);

        frameForProperties.getContentPane().add(scrollPaneForList);
        frameForProperties.setVisible(true);
    
    }

}
