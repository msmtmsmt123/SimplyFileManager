package lubenets.vladyslav.file.manager.labels;

import lubenets.vladyslav.file.manager.view.FileView;
import lubenets.vladyslav.file.manager.view.FileViewFactory;

public class LabelFileViewFactory implements FileViewFactory {

	public FileView createFileView() {
		return new LabelFileView();
	}

}
