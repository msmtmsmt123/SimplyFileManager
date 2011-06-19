package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class OpenWithCommand extends Cmd {

    public OpenWithCommand() {
        strCommand = ACTION_OPEN_WITH;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().openWithCommand();
    }

}
