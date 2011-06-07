package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;


public interface OpenFileModule {

	static final String FILE_ASSOSIATION_CFG = "fileAssosiation.cfg";

	void openThis(FileAssosiationDetecter fad, String response, Object value);

}
