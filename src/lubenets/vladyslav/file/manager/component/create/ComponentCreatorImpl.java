package lubenets.vladyslav.file.manager.component.create;

import java.awt.Label;
import java.io.File;


@SuppressWarnings("serial")
public class ComponentCreatorImpl extends Label implements ComponentCreator {

	public void setData(File file) {
		setText(file.getAbsolutePath());
		
	}

}
