package lubenets.vladyslav.file.manager.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsModel extends ApplicationModel {
//    public Map<String, String> fileAssosiationMap = new HashMap<String, String>();
    public final String FILE_ASSOSIATION_CFG = "ucf";
    Properties defaultProps = new Properties();
    String keyFrmPositionX = "keyFrmPositionX";
    String keyFrmPositionY = "keyFrmPositionY";
    String keyFrameHeight = "keyFrameHeight";
    String keyFrameWidth = "keyFrameWidth";
    private Point framePosition = new Point();
    private Dimension frameSize = new Dimension();

//    Properties applicationProps = new Properties();

    public Point getFramePosition() {
        if (defaultProps.getProperty(keyFrmPositionX) != null && defaultProps.getProperty(keyFrmPositionY) != null) {
            Integer framePositionX = Integer.valueOf(defaultProps.getProperty(keyFrmPositionX));
            Integer framePositionY = Integer.valueOf(defaultProps.getProperty(keyFrmPositionY));
            framePosition.x = framePositionX;
            framePosition.y = framePositionY;
        } else {
            framePosition.x = 0;
            framePosition.y = 0;
        }
        return framePosition;
    }

    public void setFramePosition(Point framePosition) {
        this.framePosition = framePosition;
        defaultProps.setProperty(keyFrmPositionX, String.valueOf(framePosition.x));
        defaultProps.setProperty(keyFrmPositionY, String.valueOf(framePosition.y));
        writeSettingsToTheFile();
    }

    public Dimension getFrameSize() {
        Integer frameHeight = Integer.valueOf(defaultProps.getProperty(keyFrameHeight));
        Integer frameWidth = Integer.valueOf(defaultProps.getProperty(keyFrameWidth));
        frameSize.height = frameHeight;
        frameSize.width = frameWidth;
        return frameSize;
    }

    public void setFrameSize(Dimension frameSize) {
        this.frameSize = frameSize;
        defaultProps.setProperty(keyFrameHeight, String.valueOf(frameSize.height));
        defaultProps.setProperty(keyFrameWidth, String.valueOf(frameSize.width));
        writeSettingsToTheFile();
    }

    public SettingsModel(Application application) {
        super(application);
        loadSettingsFromTheFile();
    }

    public String getLastCommand(String fileType) {
        if (defaultProps != null) {
            if (defaultProps.containsKey(fileType)) {
                return defaultProps.getProperty(fileType);
            } else
                return null;
        }
        return null;
    }

    public void setLastCommand(String fileType, String commandForFileOpenning) {
        defaultProps.setProperty(fileType, commandForFileOpenning);
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
//                applicationProps = new Properties(defaultProps);

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
            defaultProps.store(out, "Recorded");
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
