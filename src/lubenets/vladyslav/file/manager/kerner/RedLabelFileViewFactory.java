package lubenets.vladyslav.file.manager.kerner;

public class RedLabelFileViewFactory implements FileViewFactory {

	public FileView createFileView() {
		return new RedLabelFileView();
	}

}
