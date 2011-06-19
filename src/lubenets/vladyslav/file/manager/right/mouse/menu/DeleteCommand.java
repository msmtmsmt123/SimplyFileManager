package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class DeleteCommand extends Cmd {

    public DeleteCommand() {
        strCommand = DELETE;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().deleteCommand();
        

    }

}
