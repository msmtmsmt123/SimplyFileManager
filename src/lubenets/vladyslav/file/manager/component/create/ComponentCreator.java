package lubenets.vladyslav.file.manager.component.create;

import java.awt.Label;
import java.io.File;


@SuppressWarnings("serial")
public class ComponentCreator extends Label {

	public void setData(File file) {
		setText(file.getAbsolutePath());
		
	}

}
