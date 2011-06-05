package lubenets.vladyslav.file.manager.kerner;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataToShareImpl implements DataToShare, Serializable {

	int dataToShare;

	public int getDataToShare() {
		return dataToShare;
	}

	public void setDataToShare(int dataToShare) {
		this.dataToShare = dataToShare;
	}
	
	
	
}
