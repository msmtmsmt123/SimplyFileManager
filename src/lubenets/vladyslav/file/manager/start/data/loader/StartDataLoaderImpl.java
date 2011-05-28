package lubenets.vladyslav.file.manager.start.data.loader;

import java.io.File;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class StartDataLoaderImpl implements StartDataLoader {

	@Override
	public void loadInformation(GUICreatorImpl guiCreatorImpl) {
		
		guiCreatorImpl.fileList = File.listRoots();		
		guiCreatorImpl.data = new String[guiCreatorImpl.fileList.length];
		for (int i = 0; i < guiCreatorImpl.fileList.length; i++) {
			guiCreatorImpl.data[i] = guiCreatorImpl.fileList[i].getPath();
			guiCreatorImpl.lm.add(i, guiCreatorImpl.data[i]);
		}

	}

}
