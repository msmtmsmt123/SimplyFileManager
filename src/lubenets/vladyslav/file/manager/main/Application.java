package lubenets.vladyslav.file.manager.main;

public class Application {

    private FileModel fileModel;
    private SettingsModel settingsModel;
    private ViewModel viewModel;
    private Controller controller;

    public ViewModel getViewModel() {
        return viewModel;
    }

    public Controller getController() {
        return controller;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public SettingsModel getSettingsModel() {
        return settingsModel;
    }

    public void start() {
        viewModel = new ViewModel(this);
        fileModel = new FileModel(this);
        settingsModel = new SettingsModel(this);
        controller = new Controller(this);


    }
}
