package lubenets.vladyslav.file.manager.main;

import javax.swing.DefaultListModel;

public class Controller extends ApplicationModel {
    public Controller(Application application) {
        super(application);
        getApplication().getFileModel().firstRun();
        getApplication().getViewModel().setListModel(getApplication().getFileModel().modelOfTheList);
    }

    public void setDataToListModelAfterSelection(DefaultListModel lm) {

        lm.clear();
        getApplication().getFileModel().setPath();
        getApplication().getFileModel().displayFilesFromAPath();
        getApplication().getViewModel().jFrm.setTitle(getApplication().getFileModel().path);
    }

}
