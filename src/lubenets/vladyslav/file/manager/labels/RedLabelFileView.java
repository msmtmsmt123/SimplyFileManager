package lubenets.vladyslav.file.manager.labels;

import java.awt.Color;
import java.io.File;


@SuppressWarnings("serial")
public class RedLabelFileView extends LabelFileView {

	@Override
	public void setData(File file) {
		super.setData(file);
		setBackground(Color.RED);
	}

}
