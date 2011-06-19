package lubenets.vladyslav.file.manager.data.loader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StartDataLoader {
    public static File[] fileList;
    public static String[] data;
    Map<Integer, String> listModel;

	public Map<Integer, String> loadInformation() {
		listModel = new HashMap<Integer, String>();
		fileList = File.listRoots();
		data = new String[fileList.length];
		
		for (int i = 0; i < fileList.length; i++) {
			data[i] = fileList[i].getPath();
			listModel.put(i, data[i]);
		}
		return listModel;

	}

}
