package lubenets.vladyslav.file.manager.main;

public class Application {

    private FileModel fileModel;
    private SettingsModel settingsModel;
    private ViewModel viewModel;
    private Controller controller;
    private FileOperationModel fileOperationModel;

    public FileOperationModel getFileOperationModel() {
        return fileOperationModel;
        
    }
    
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
        settingsModel = new SettingsModel(this);
        viewModel = new ViewModel(this);
        fileModel = new FileModel(this);
        fileOperationModel = new FileOperationModel(this);
        controller = new Controller(this);


    }
}
