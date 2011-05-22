package lubenets.vladyslav.file.manager.kerner;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ListForModel extends AbstractListModel {

	private FileViewFactory factory = new LabelFileViewFactory();

	private Vector<File> files = new Vector<File>();

	public FileViewFactory getFactory() {
		return factory;
	}

	public void setFactory(FileViewFactory factory) {
		this.factory = factory;
	}

	ListForModel() {

	}

	public void putElement(File objectToVector) {
		files.add(objectToVector);
	}

	public int getSize() {
		return files.size();
	}

	public Component getElementAt(int index) {
		final FileView fileView = factory.createFileView();
		final Component result = (Component) fileView;
		final File data = files.elementAt(index);

		fileView.setData(data);

		result.setBounds(0, index * 50, 200, 50);
		result.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(result, data.getAbsolutePath());
			}
		});

		return result;
	}

}
