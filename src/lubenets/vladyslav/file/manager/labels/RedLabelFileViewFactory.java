package lubenets.vladyslav.file.manager.labels;

import lubenets.vladyslav.file.manager.view.FileView;
import lubenets.vladyslav.file.manager.view.FileViewFactory;

public class RedLabelFileViewFactory implements FileViewFactory {

	public FileView createFileView() {
		return new RedLabelFileView();
	}

}
