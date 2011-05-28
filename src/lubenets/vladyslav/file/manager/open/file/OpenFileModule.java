package lubenets.vladyslav.file.manager.open.file;

import lubenets.vladyslav.file.manager.last.command.FileAssosiationDetecter;


public interface OpenFileModule {

	static final String FILE_ASSOSIATION_CFG = "fileAssosiation.cfg";

	void openThis(FileAssosiationDetecter fad, String response, Object value);

}
