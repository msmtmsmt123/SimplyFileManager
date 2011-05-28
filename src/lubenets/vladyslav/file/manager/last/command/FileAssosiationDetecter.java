package lubenets.vladyslav.file.manager.last.command;

public interface FileAssosiationDetecter {

	String getLastCommand(String fileType);

	void setLastCommand(String fileType, String commandForFileOpenning);

}
