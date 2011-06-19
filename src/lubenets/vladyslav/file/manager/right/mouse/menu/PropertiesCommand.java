package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class PropertiesCommand extends Cmd {

    public PropertiesCommand() {
        strCommand = PROPERTIES;
    }

    public void execute(ViewModel localeViewModel) {
        localeViewModel.getApplication().getFileOperationModel().propertiesCommand();

    }

}
