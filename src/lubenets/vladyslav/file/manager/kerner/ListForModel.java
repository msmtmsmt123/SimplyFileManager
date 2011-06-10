package lubenets.vladyslav.file.manager.kerner;

import java.awt.Component;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractListModel;

import lubenets.vladyslav.file.manager.component.create.ComponentCreator;
import lubenets.vladyslav.file.manager.component.create.ComponentCreatorImpl;

@SuppressWarnings("serial")
public class ListForModel extends AbstractListModel {

    Vector<File> files = new Vector<File>();

    public void putElement(File objectToVector) {
        files.add(objectToVector);
    }

    public int getSize() {
        return files.size();
    }

    public Component getElementAt(int index) {
        final ComponentCreator fileView = new ComponentCreatorImpl();
        final File data = files.elementAt(index);
        fileView.setData(data);
        final Component result = (Component) fileView;

        return result;
    }

}
