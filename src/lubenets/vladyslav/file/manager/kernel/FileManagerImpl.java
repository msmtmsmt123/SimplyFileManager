package lubenets.vladyslav.file.manager.kernel;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class FileManagerImpl implements FileManager, Serializable {

	
	public File[] createFileList(String path) {
		
		File fl = new File(path);
		File[] fileList = fl.listFiles();
		return fileList;
		
	}
	
	
	
}
