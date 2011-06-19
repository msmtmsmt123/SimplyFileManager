package lubenets.vladyslav.file.manager.kernel;

import java.io.File;

import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;

public interface ReadAndWriteSettings {

    void readFromFile(File file);

    void writeToFile(FileAssosiationDetecter fad);

}
