package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class CutCommand extends Cmd {

    public CutCommand() {
        strCommand = CUT;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().cutCommand();

    }

}
