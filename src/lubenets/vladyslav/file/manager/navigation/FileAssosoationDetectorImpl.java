package lubenets.vladyslav.file.manager.navigation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class FileAssosoationDetectorImpl implements FileAssosiationDetecter, Serializable{
    public Map<String, String> fileAssosiationMap = new HashMap<String, String>();
	private static final long serialVersionUID = 1L;
	


	public String getLastCommand(String fileType) {
		if (fileAssosiationMap.containsKey(fileType))
			return fileAssosiationMap.get(fileType);
		return null;
	}

	public void setLastCommand(String fileType, String commandForFileOpenning) {
		fileAssosiationMap.put(fileType, commandForFileOpenning);
	}

}
