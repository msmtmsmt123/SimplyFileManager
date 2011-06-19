package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class CopyCommand extends Cmd {

    public CopyCommand() {
        strCommand = COPY;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().copyCommand();

    }

}
