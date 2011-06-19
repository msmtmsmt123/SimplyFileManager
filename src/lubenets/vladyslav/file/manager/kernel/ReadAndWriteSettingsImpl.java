package lubenets.vladyslav.file.manager.kernel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.controller.GUICreatorImpl;
import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;

public class ReadAndWriteSettingsImpl implements ReadAndWriteSettings {

    @SuppressWarnings("unchecked")
    public void readFromFile(File file) {
        if (file.exists()) {
            try {

                FileInputStream fis = new FileInputStream(GUICreatorImpl.FILE_ASSOSIATION_CFG);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object tempObject = ois.readObject();
                Class<FileAssosiationDetecter> classObject = (Class<FileAssosiationDetecter>) tempObject.getClass();
                if (classObject.equals(GUICreatorImpl.fad.getClass())) {
                    GUICreatorImpl.fad = (FileAssosiationDetecter) tempObject;
                    ois.close();
                } else throw new ClassNotFoundException();

            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "File not found!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Input-output error!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Error to load programm settings!");
                return;
            }

        }
    }

    public void writeToFile(FileAssosiationDetecter fad) {
        try {
            FileOutputStream fos = new FileOutputStream(GUICreatorImpl.FILE_ASSOSIATION_CFG);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(fad);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
