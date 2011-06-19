package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class RenameCommand extends Cmd {
    public RenameCommand() {
        strCommand = RENAME;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().renameCommand();
    }

}
