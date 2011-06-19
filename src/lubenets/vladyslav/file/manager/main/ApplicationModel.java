package lubenets.vladyslav.file.manager.main;

public class ApplicationModel {
        private final Application application;
    
        public Application getApplication() {
            return application;
        }
    
        public ApplicationModel(Application application) {
            if (application == null) throw new NullPointerException();
            this.application = application;
        }
}
