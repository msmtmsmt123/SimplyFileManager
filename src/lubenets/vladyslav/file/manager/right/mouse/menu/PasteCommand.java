package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class PasteCommand extends Cmd {

    public PasteCommand() {
        strCommand = PASTE;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().pasteCommand();
   }



}
