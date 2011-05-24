package lubenets.vladyslav.file.manager.labels;

import java.awt.Label;
import java.io.File;

import lubenets.vladyslav.file.manager.view.FileView;

@SuppressWarnings("serial")
public class LabelFileView extends Label implements FileView {

	public void setData(File file) {
		setText(file.getAbsolutePath());
	}

}
