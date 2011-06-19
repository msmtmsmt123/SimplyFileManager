package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import lubenets.vladyslav.file.manager.controller.GUICreatorImpl;

public class PopUpMenu {

    public String strAction;
    Invoker objInvoker;

    @SuppressWarnings("serial")
    public void activate() {

        objInvoker = new Invoker();

//        GUICreatorImpl.menu = new JPopupMenu();

        Action openWith = new AbstractAction(GUICreatorImpl.ACTION_OPEN_WITH) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.ACTION_OPEN_WITH;
                executeCommand(strAction);
            }

        };

        Action copy = new AbstractAction(GUICreatorImpl.COPY) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.COPY;
                executeCommand(strAction);            }
        };

        Action cut = new AbstractAction(GUICreatorImpl.CUT) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.CUT;
                executeCommand(strAction);            }
        };

        Action paste = new AbstractAction(GUICreatorImpl.PASTE) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.PASTE;
                executeCommand(strAction);            }
        };

        Action rename = new AbstractAction(GUICreatorImpl.RENAME) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.RENAME;
                executeCommand(strAction);            }
        };

        Action delete = new AbstractAction(GUICreatorImpl.DELETE) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.DELETE;
                executeCommand(strAction);            }
        };

        Action properties = new AbstractAction(GUICreatorImpl.PROPERTIES) {

            public void actionPerformed(ActionEvent e) {
                strAction = GUICreatorImpl.PROPERTIES;
                executeCommand(strAction);            }
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

    public void executeCommand(String strAction) {

        if (strAction != null) {
            Command executingCommand = objInvoker.getCommand(strAction);
            executingCommand.execute();
        }
    }

}
