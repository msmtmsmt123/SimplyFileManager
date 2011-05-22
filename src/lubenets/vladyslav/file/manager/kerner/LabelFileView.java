package lubenets.vladyslav.file.manager.kerner;

import java.awt.Label;
import java.io.File;

@SuppressWarnings("serial")
public class LabelFileView extends Label implements FileView {

	public void setData(File file) {
		setText(file.getAbsolutePath());
	}

}
