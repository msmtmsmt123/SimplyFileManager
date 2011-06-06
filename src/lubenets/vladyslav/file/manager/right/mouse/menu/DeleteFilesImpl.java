package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.io.File;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class DeleteFilesImpl implements DeleteFiles {

	@Override
	public void removeFiles(File source) {
		File[] fileList = source.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					removeFiles(fileList[i]);
					if (!fileList[i].delete()) {
						JOptionPane.showMessageDialog(GUICreatorImpl.jFrm,
						"Can not delete a file!");
					}
				} else {
					if (!fileList[i].delete()) {
						JOptionPane.showMessageDialog(GUICreatorImpl.jFrm,
						"Can not delete a file!");
					}
					
				}
			}
		}
		if (!source.delete()) {
			JOptionPane.showMessageDialog(GUICreatorImpl.jFrm,
					"Can not create a directory!");
		}

	}

}
