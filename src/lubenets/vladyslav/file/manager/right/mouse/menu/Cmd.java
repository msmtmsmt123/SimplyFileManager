package lubenets.vladyslav.file.manager.right.mouse.menu;

import lubenets.vladyslav.file.manager.main.ViewModel;

public class Cmd {
    public  final String ACTION_OPEN_WITH = "Open with...";
    public  final String COPY = "Copy";
    public  final String CUT = "Cut";
    public  final String PASTE = "Paste";
    public  final String RENAME = "Rename";
    public  final String DELETE = "Delete";
    public  final String PROPERTIES = "Properties";
    
    public String strCommand = "";
    public boolean filesMustMove = true;
    public void execute(ViewModel localeViewModel) {
    }
}
