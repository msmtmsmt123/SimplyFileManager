package lubenets.vladyslav.file.manager.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SettingsModel extends ApplicationModel {
    public Map<String, String> fileAssosiationMap = new HashMap<String, String>();
    private static final long serialVersionUID = 1L;
    public final String FILE_ASSOSIATION_CFG = "ucf";
    Properties defaultProps = new Properties();
    Properties applicationProps;

    public SettingsModel(Application application) {
        super(application);
        loadSettingsFromTheFile();
    }

    public String getLastCommand(String fileType) {
        if (applicationProps.containsKey(fileType)) {
            return applicationProps.getProperty(fileType);
        } else
            return null;
    }

    public void setLastCommand(String fileType, String commandForFileOpenning) {
        applicationProps.setProperty(fileType, commandForFileOpenning);
        writeSettingsToTheFile();
    }

    public void loadSettingsFromTheFile() {
        // create and load default properties
        FileInputStream in;
        File confFile = new File(FILE_ASSOSIATION_CFG);
        if (confFile.exists()) {
            try {
                in = new FileInputStream(FILE_ASSOSIATION_CFG);
                defaultProps.load(in);
                in.close();

                // create application properties with default
                applicationProps = new Properties(defaultProps);

            } catch (FileNotFoundException ex) {
                getApplication().getController().showDialog("File not found!");
                ex.printStackTrace();
            } catch (IOException ex) {
                getApplication().getController().showDialog("Input-output error!");
                ex.printStackTrace();
            }
        }

    }

    public void writeSettingsToTheFile() {
        FileOutputStream out;
        try {
            out = new FileOutputStream(FILE_ASSOSIATION_CFG);
            applicationProps.store(out, "Recorded");
            out.close();
        } catch (FileNotFoundException ex) {
            getApplication().getController().showDialog("File not found!");
            ex.printStackTrace();
        } catch (IOException ex) {
            getApplication().getController().showDialog("Input-output error!");
            ex.printStackTrace();
        }
    }

}
