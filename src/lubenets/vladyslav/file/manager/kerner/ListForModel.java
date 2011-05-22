package lubenets.vladyslav.file.manager.kerner;

import java.awt.Component;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

public class ListForModel extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<File> files = new Vector<File>();

	ListForModel() {

	}

	public void putElement(File objectToVector) {
		files.add(objectToVector);
	}

	public int getSize() {
		return files.size();
	}

	public Component getElementAt(int index) {
		final String value = files.elementAt(index).getAbsolutePath();
		final Label result = new Label(value);

		result.setBounds(0, index * 50, 200, 50);
		result.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(result, value);
			}
		});

		return result;
	}

}
