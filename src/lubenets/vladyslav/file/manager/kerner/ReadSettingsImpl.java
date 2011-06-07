package lubenets.vladyslav.file.manager.kerner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileModule;

public class ReadSettingsImpl implements ReadSettings {

    public void readFromFile(File file) {
        if (file.exists()) {
            try {

                FileInputStream fis = new FileInputStream(
                        OpenFileModule.FILE_ASSOSIATION_CFG);
                ObjectInputStream ois = new ObjectInputStream(fis);
                GUICreatorImpl.fad = (FileAssosiationDetecter) ois.readObject();
                ois.close();

            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "File not found!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "Input-output error!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(GUICreatorImpl.jFrm, "System error occured!");
                e.printStackTrace();
            }
        }
    }

}
