package lubenets.vladyslav.file.manager.kerner;

import java.awt.Color;
import java.io.File;

import lubenets.vladyslav.file.manager.kerner.LabelFileView;

public class RedLabelFileView extends LabelFileView {

	@Override
	public void setData(File file) {
		super.setData(file);
		setBackground(Color.RED);
	}

}
