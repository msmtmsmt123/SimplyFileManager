package lubenets.vladyslav.file.manager.open.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import lubenets.vladyslav.file.manager.last.command.FileAssosiationDetecter;

public class OpenFileModuleImpl implements OpenFileModule {

	@Override
	public void openThis(FileAssosiationDetecter fad, String fileToOpen,
			Object value) {
		Runtime r = Runtime.getRuntime();
		String response;

		String fileType = value.toString().substring(
				value.toString().lastIndexOf('.') + 1,
				value.toString().length());

		String lastCommandForFileOpenning = fad.getLastCommand(fileType);

		if (lastCommandForFileOpenning == null) {
			response = JOptionPane
					.showInputDialog("Enter a program name to open file");
		} else
			response = JOptionPane.showInputDialog(
					"Enter a program name to open file",
					lastCommandForFileOpenning);

		if (response == null || response.length() == 0) {
			return;
		}

		fad.setLastCommand(fileType, response);

		String parameters[] = { response, fileToOpen };

		try {
			r.exec(parameters);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(FILE_ASSOSIATION_CFG);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(fad);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
