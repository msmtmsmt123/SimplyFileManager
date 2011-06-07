package lubenets.vladyslav.file.manager.navigation;

public interface FileAssosiationDetecter {

	String getLastCommand(String fileType);

	void setLastCommand(String fileType, String commandForFileOpenning);

}
