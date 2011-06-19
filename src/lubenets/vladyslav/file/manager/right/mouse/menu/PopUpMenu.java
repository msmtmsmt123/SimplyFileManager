package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import lubenets.vladyslav.file.manager.main.ViewModel;


public class PopUpMenu extends Cmd {

    public String strAction;
    Invoker objInvoker;
    private ViewModel localeViewModel;
    
    @SuppressWarnings("serial")
    public void activate(ViewModel viewModel) {

        this.localeViewModel = viewModel;
        objInvoker = new Invoker();

//        GUICreatorImpl.menu = new JPopupMenu();

        Action openWith = new AbstractAction(ACTION_OPEN_WITH) {

            public void actionPerformed(ActionEvent e) {
                strAction = ACTION_OPEN_WITH;
                executeCommand(strAction);
            }

        };

        Action copy = new AbstractAction(COPY) {

            public void actionPerformed(ActionEvent e) {
                strAction = COPY;
                executeCommand(strAction);            }
        };

        Action cut = new AbstractAction(CUT) {

            public void actionPerformed(ActionEvent e) {
                strAction = CUT;
                executeCommand(strAction);            }
        };

        Action paste = new AbstractAction(PASTE) {

            public void actionPerformed(ActionEvent e) {
                strAction = PASTE;
                executeCommand(strAction);            }
        };

        Action rename = new AbstractAction(RENAME) {

            public void actionPerformed(ActionEvent e) {
                strAction = RENAME;
                executeCommand(strAction);            }
        };

        Action delete = new AbstractAction(DELETE) {

            public void actionPerformed(ActionEvent e) {
                strAction = DELETE;
                executeCommand(strAction);            }
        };

        Action properties = new AbstractAction(PROPERTIES) {

            public void actionPerformed(ActionEvent e) {
                strAction = PROPERTIES;
                executeCommand(strAction);            }
        };

        viewModel.jList.getActionMap().put(ACTION_OPEN_WITH, openWith);
        viewModel.menu.add(openWith);
        viewModel.jList.getActionMap().put(COPY, copy);
        viewModel.menu.add(copy);
        viewModel.jList.getActionMap().put(CUT, cut);
        viewModel.menu.add(cut);
        viewModel.jList.getActionMap().put(RENAME, rename);
        viewModel.menu.add(rename);
        viewModel.jList.getActionMap().put(PASTE, paste);
        viewModel.menu.add(paste);
        viewModel.jList.getActionMap().put(DELETE, delete);
        viewModel.menu.add(delete);
        viewModel.jList.getActionMap().put(PROPERTIES, properties);
        viewModel.menu.add(properties);

    }

    public void executeCommand(String strAction) {

        if (strAction != null) {
            Cmd executingCommand = objInvoker.getCommand(strAction);
            executingCommand.execute(localeViewModel);
        }
    }

}
