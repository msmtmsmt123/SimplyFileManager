package lubenets.vladyslav.file.manager.copy.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFilesImpl implements CopyFiles {

	public void copingFiles(String source, String destination) {
		File dir = new File(source);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				File newDir = new File(destination, fileList[i].getName());
				newDir.mkdir();
				copingFiles(fileList[i].getAbsolutePath(),
						newDir.getAbsolutePath());
			} else {
				File newFile = new File(destination, fileList[i].getName());
				copyFile(fileList[i].getAbsolutePath(), newFile);
			}
		}

	}

	private void copyFile(String absolutePath, File newFile) {
		File source = new File(absolutePath);
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(source);
			out = new FileOutputStream(newFile);
			int tempInt;
			while ((tempInt = in.read()) != -1) {
				out.write(tempInt);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
