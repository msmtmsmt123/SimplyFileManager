package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;
import lubenets.vladyslav.file.manager.navigation.FilePropertiesDetectorImpl;



public class RightMouseMenuImpl implements RightMouseMenu {

	
	
	@SuppressWarnings("serial")
    public void activate() {
        
	    GUICreatorImpl.menu = new JPopupMenu();

        Action openWith = new AbstractAction(GUICreatorImpl.ACTION_OPEN_WITH) {

            public void actionPerformed(ActionEvent e) {
                OpenFileWith ofw = new OpenFileWithImpl();
                ofw.openThis();
            }
        };
        Action copy = new AbstractAction(GUICreatorImpl.COPY) {

            public void actionPerformed(ActionEvent e) {
                CopyFile cf = new CopyFileImpl();
                cf.copyThis();
            }
        };
        Action cut = new AbstractAction(GUICreatorImpl.CUT) {

            public void actionPerformed(ActionEvent e) {
                CutFile cf = new CutFileImpl();
                cf.copyThis();
            }
        };
        Action paste = new AbstractAction(GUICreatorImpl.PASTE) {

            public void actionPerformed(ActionEvent e) {
                PasteFile pf = new PasteFileImpl();
                pf.pasteThis();

            }
        };
        Action rename = new AbstractAction(GUICreatorImpl.RENAME) {

            public void actionPerformed(ActionEvent e) {
                RenameFile rf = new RenameFileImpl();
                rf.renameThis();
            }
        };
        Action delete = new AbstractAction(GUICreatorImpl.DELETE) {

            public void actionPerformed(ActionEvent e) {
                DeleteFile dfi = new DeleteFileImpl();
                dfi.deleteThis();
            }
        };
        Action properties = new AbstractAction(GUICreatorImpl.PROPERTIES) {

            public void actionPerformed(ActionEvent e) {
                FilePropertiesDetector fpd = new FilePropertiesDetectorImpl();
                fpd.showProperties();
            }
        };

        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.ACTION_OPEN_WITH, openWith);
        GUICreatorImpl.menu.add(openWith);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.COPY, copy);
        GUICreatorImpl.menu.add(copy);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.CUT, cut);
        GUICreatorImpl.menu.add(cut);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.RENAME, rename);
        GUICreatorImpl.menu.add(rename);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.PASTE, paste);
        GUICreatorImpl.menu.add(paste);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.DELETE, delete);
        GUICreatorImpl.menu.add(delete);
        GUICreatorImpl.jList.getActionMap().put(GUICreatorImpl.PROPERTIES, properties);
        GUICreatorImpl.menu.add(properties);

		
	}
	

}
