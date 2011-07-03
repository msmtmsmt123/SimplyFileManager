package lubenets.vladyslav.file.manager.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class FileOperationModel extends ApplicationModel {

    public FileOperationModel(Application application) {
        super(application);
    }

//OpenWith Command    
    public void openWithCommand() {
        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);

        File fileType = new File(getApplication().getFileModel().path + File.separator + value);
        if (getApplication().getFileModel().path == null) {
            fileType = new File(value);
            getApplication().getFileModel().path = value;
        }
        if (fileType.isFile()) {
            String fileToOpen = getApplication().getFileModel().path + File.separator + value;
            openThis(fileToOpen, value);
        } else {
            if (getApplication().getFileModel().path.equals(File.separator)) {
                getApplication().getFileModel().path = getApplication().getFileModel().path + value;
            } else
                getApplication().getFileModel().path = getApplication().getFileModel().path + File.separator + value;

            getApplication().getFileModel().displayFilesFromAPath();
        }
    }

    void openThis(String fileToOpen, Object value) {
        Runtime r = Runtime.getRuntime();
        String response;

        String fileType = value.toString().substring(value.toString().lastIndexOf('.') + 1, value.toString().length());

        String lastCommandForFileOpenning = getApplication().getSettingsModel().getLastCommand(fileType);

        if (lastCommandForFileOpenning == null) {
            response = getApplication().getController().showID("Enter a program name to open file");
        } else
            response = lastCommandForFileOpenning;

        if (response == null || response.length() == 0) {
            return;
        }

        getApplication().getSettingsModel().setLastCommand(fileType, response);

        String parameters[] = { response, fileToOpen };

        try {
            r.exec(parameters);
        } catch (IOException e1) {
            getApplication().getController().showDialog("Error to run a programm!");
            return;
        }

    }

//Copy command    
    public void copyCommand() {
        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
        File source = new File(getApplication().getFileModel().path + File.separator + value);
        getApplication().getFileModel().buffer = source.getAbsolutePath();
        getApplication().getFileModel().filesMustMove = false;
    }


//Cut command        
    public void cutCommand() {
        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
        File source = new File(getApplication().getFileModel().path + File.separator + value);
        getApplication().getFileModel().buffer = source.getAbsolutePath();
        getApplication().getFileModel().filesMustMove = true;
    }

//Paste command
    public void pasteCommand() {
        File destination;
//        String value = (String) GUICreatorImpl.lm.toString();
        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
        if (value == null || (value.equals(".."))) {
            destination = new File(getApplication().getFileModel().path);
        } else
            destination = new File(getApplication().getFileModel().path + File.separator + value);

        copingFilesP(getApplication().getFileModel().buffer, destination.getAbsolutePath());
        if (getApplication().getFileModel().filesMustMove) {
            File toDelete = new File(getApplication().getFileModel().buffer);
            removeFilesP(toDelete);
        }
        getApplication().getFileModel().displayFilesFromAPath();

    }

    void copingFilesP(String source, String destination) {
        File dir = new File(source);
        if (dir.isDirectory()) {
            dir = new File((destination + File.separator + source.substring(source.lastIndexOf(File.separator) + 1)));
            boolean mkdr = dir.mkdir();
            if (!mkdr) {
                getApplication().getController().showDialog("Can not create a directory!");
            }
            destination = dir.getAbsolutePath();
            dir = new File(source);
            
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    File newDir = new File(destination, fileList[i].getName());
                    mkdr = newDir.mkdir();
                    if (!mkdr) {
                        getApplication().getController().showDialog("Can not create a directory!");
                    }
                    copingFilesP(fileList[i].getAbsolutePath(), newDir.getAbsolutePath());
                } else {
                    File newFile = new File(destination, fileList[i].getName());
                    copyFileP(fileList[i].getAbsolutePath(), newFile);
                }
            }
        }
        if (dir.isFile()) {
            File newDir = new File(destination, source.substring(source.lastIndexOf(File.separator) + 1));
            copyFileP(source, newDir);
        }
    }

    void copyFileP(String absolutePath, File newFile) {
        File source = new File(absolutePath);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(newFile);
            int tempInt;
            while ((tempInt = in.read()) != -1) {
                out.write(tempInt);
            }
        } catch (FileNotFoundException e) {
            getApplication().getController().showDialog("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            getApplication().getController().showDialog("IO Exception!");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    getApplication().getController().showDialog("IO Exception!");
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    getApplication().getController().showDialog("IO Exception!");
                    e.printStackTrace();
                }
            }

        }

    }

    void removeFilesP(File source) {
        File[] fileList = source.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    removeFilesP(fileList[i]);
                    if (!fileList[i].delete() && fileList[i].exists()) {
                        getApplication().getController().showDialog("Can not delete a file!");
                    }
                } else {
                    if (!fileList[i].delete() && fileList[i].exists()) {
                        getApplication().getController().showDialog("Can not delete a file!");
                    }

                }
            }
        }
        if (!source.delete() && source.exists()) {
            getApplication().getController().showDialog("Can not create a directory!");
        }

    }

//Rename command
    public void renameCommand() {

        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
        File source = new File(getApplication().getFileModel().path + File.separator + value);
        String returnedPath = JOptionPane.showInputDialog("Enter a new file name", value);
        if (returnedPath == null) {
            return;
        }
        File response = new File(getApplication().getFileModel().path + File.separator + returnedPath);
        boolean result = source.renameTo(response);
        if (!result || returnedPath == null) {
            getApplication().getController().showDialog("I can`t rename this!");
        }
        getApplication().getFileModel().displayFilesFromAPath();
    }

// DeleteCommand
    public void deleteCommand() {
        String value = (String) getApplication().getViewModel().lm.get(getApplication().getFileModel().selectedIndex);
        File source = new File(getApplication().getFileModel().path + File.separator + value);
//Ask for delete
        String response = getApplication().getController().askForDelete();

        if (response.equals("yes")) {
            removeFilesD(source);
            getApplication().getFileModel().displayFilesFromAPath();
        }

    }

    public void removeFilesD(File source) {
        File[] fileList = source.listFiles();
        boolean check;
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                check = fileList[i].isDirectory();
                if (check) {
                    removeFilesD(fileList[i]);
                    check = fileList[i].delete();
                    boolean exist = fileList[i].exists();
                    if (!check && exist) {
                        getApplication().getController().showDialog("Can not delete a file!");
                    }
                } else {
                    check = fileList[i].delete();
                    if (!check && fileList[i].exists()) {
                        getApplication().getController().showDialog("Can not delete a file!");
                    }

                }
            }
        }
        if (!source.delete() && source.exists()) {
            getApplication().getController().showDialog("Can not create a directory!");
        }

    }

//Properties command    
    public void propertiesCommand() {

        String file = null;
        String fileSize = null;
        String parent;
        String hidden;
        String dateOfTheLastModified;
        String writeReadPermission;

        String value = (String) getApplication().getFileModel().modelOfTheList.get(getApplication().getFileModel().selectedIndex);
        File source = new File(getApplication().getFileModel().path + File.separator + value);

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
                writeReadPermission = source.getPath() + " is read-write";
            else
                writeReadPermission = source.getPath() + " is write only";
        } else {
            if (source.canRead())
                writeReadPermission = source.getPath() + " is read only";
            else
                writeReadPermission = "Permission to " + source.getPath() + " denied";
        }

        if (source.getParent() == null) {
            parent = source.getPath() + " is a root directory.";
        } else {
            parent = "Parent of " + source.getPath() + " is " + source.getParent() + ".";
        }

        if (source.isHidden()) {
            hidden = source.getPath() + " is Hidden.";
        } else
            hidden = source.getPath() + " is not Hidden.";

        dateOfTheLastModified = source.getPath() + " was last modified on " + new java.util.Date(source.lastModified());

        String[] infoForList = { file, fileSize, parent, hidden, dateOfTheLastModified, writeReadPermission };

        getApplication().getController().showProperties(infoForList);

    }

}
