package lubenets.vladyslav.file.manager.kerner;

import java.io.File;

public class FileManagerImpl implements FileManager {

	
	public File[] createFileList(String path) {
		
		File fl = new File(path);
		File[] fileList = fl.listFiles();
		return fileList;
		
	}
	
	
	
}
