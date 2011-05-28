package lubenets.vladyslav.file.manager.last.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FileAssosoationDetectorImpl implements FileAssosiationDetecter, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Map<String, String> fileAssosiationMap = new HashMap<String, String>();

	@Override
	public String getLastCommand(String fileType) {
		if (fileAssosiationMap.containsKey(fileType))
			return fileAssosiationMap.get(fileType);
		return null;
	}

	@Override
	public void setLastCommand(String fileType, String commandForFileOpenning) {
		fileAssosiationMap.put(fileType, commandForFileOpenning);
	}

}
