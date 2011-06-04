package lubenets.vladyslav.file.manager.delete.files;

import java.io.File;

public class DeleteFilesImpl implements DeleteFiles {

	@Override
	public void removeFiles(File source) {
		File[] fileList = source.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					removeFiles(fileList[i]);
					fileList[i].delete();
				} else {
					fileList[i].delete();
				}
			}
		}
		source.delete();
	}

}
