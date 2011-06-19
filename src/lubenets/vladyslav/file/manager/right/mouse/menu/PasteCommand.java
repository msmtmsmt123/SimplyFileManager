package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.controller.GUICreatorImpl;

public class PasteCommand extends Command {

    public PasteCommand() {
        strCommand = GUICreatorImpl.PASTE;
    }

    public void execute() {
        File destination;
        String value = (String) GUICreatorImpl.lm.toString();
        if (value == null || (value.equals(".."))) {
            destination = new File(GUICreatorImpl.path);
        } else
            destination = new File(GUICreatorImpl.path + File.separator + value);

        copingFiles(GUICreatorImpl.buffer, destination.getAbsolutePath());
        if (filesMustMove) {
            File toDelete = new File(GUICreatorImpl.buffer);
            removeFiles(toDelete);
        }
        GUICreatorImpl.fl.fillData();

    }

    public void copingFiles(String source, String destination) {
        File dir = new File(source);
        if (!dir.mkdir()) {
            JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not create a directory!");
        }

        File[] fileList = dir.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                File newDir = new File(destination, fileList[i].getName());
                if (!newDir.mkdir()) {
                    JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not create a directory!");
                }
                copingFiles(fileList[i].getAbsolutePath(), newDir.getAbsolutePath());
            } else {
                File newFile = new File(destination, fileList[i].getName());
                copyFile(fileList[i].getAbsolutePath(), newFile);
            }
        }

    }

    private void copyFile(String absolutePath, File newFile) {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    public void removeFiles(File source) {
        File[] fileList = source.listFiles();
        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    removeFiles(fileList[i]);
                    if (!fileList[i].delete()) {
                        JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not delete a file!");
                    }
                } else {
                    if (!fileList[i].delete()) {
                        JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not delete a file!");
                    }

                }
            }
        }
        if (!source.delete()) {
            JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Can not create a directory!");
        }

    }

}
