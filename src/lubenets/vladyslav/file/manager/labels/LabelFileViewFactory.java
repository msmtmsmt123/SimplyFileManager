package lubenets.vladyslav.file.manager.labels;

import java.io.Serializable;

import lubenets.vladyslav.file.manager.view.FileView;
import lubenets.vladyslav.file.manager.view.FileViewFactory;

@SuppressWarnings("serial")
public class LabelFileViewFactory implements FileViewFactory, Serializable {

	public FileView createFileView() {
		return new LabelFileView();
	}

}
