package lubenets.vladyslav.file.manager.kerner;

import java.awt.Component;
import java.util.Vector;

import javax.swing.AbstractListModel;

public class ListForModel extends AbstractListModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Object> fileVector = new Vector<Object>();
	
	ListForModel() {
		
	}
	
	public void putElement(Object objectToVector) {
		fileVector.add(objectToVector);
	}
	
	@Override
	public int getSize() {
		return fileVector.size();
	}

	@Override
	public Component getElementAt(int index) {
		return (Component) fileVector.elementAt(index);
	} 

}
