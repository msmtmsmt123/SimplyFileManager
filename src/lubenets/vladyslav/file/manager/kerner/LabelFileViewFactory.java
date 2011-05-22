package lubenets.vladyslav.file.manager.kerner;

public class LabelFileViewFactory implements FileViewFactory {

	public FileView createFileView() {
		return new LabelFileView();
	}

}
