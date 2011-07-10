package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import lubenets.vladyslav.file.manager.main.ViewModel;


public class PopUpMenu extends Cmd {

    public String strAction;
    Invoker objInvoker;
    private ViewModel localeViewModel;
    Action openWith;
    Action copy;
    Action cut;
    Action paste;
    Action rename;
    Action delete;
    Action properties;
    
    @SuppressWarnings("serial")
    public void activate(ViewModel viewModel) {

        this.localeViewModel = viewModel;
        objInvoker = new Invoker();

        openWith = new AbstractAction(ACTION_OPEN_WITH) {

            public void actionPerformed(ActionEvent e) {
                strAction = ACTION_OPEN_WITH;
                executeCommand(strAction);
            }

        };

        copy = new AbstractAction(COPY) {

            public void actionPerformed(ActionEvent e) {
                strAction = COPY;
                executeCommand(strAction);            }
        };

        cut = new AbstractAction(CUT) {

            public void actionPerformed(ActionEvent e) {
                strAction = CUT;
                executeCommand(strAction);            }
        };

        paste = new AbstractAction(PASTE) {

            public void actionPerformed(ActionEvent e) {
                strAction = PASTE;
                executeCommand(strAction);            }
        };

        rename = new AbstractAction(RENAME) {

            public void actionPerformed(ActionEvent e) {
                strAction = RENAME;
                executeCommand(strAction);            }
        };

        delete = new AbstractAction(DELETE) {

            public void actionPerformed(ActionEvent e) {
                strAction = DELETE;
                executeCommand(strAction);            }
        };

        properties = new AbstractAction(PROPERTIES) {

            public void actionPerformed(ActionEvent e) {
                strAction = PROPERTIES;
                executeCommand(strAction);            }
        };

        viewModel.jList.getActionMap().put(ACTION_OPEN_WITH, openWith);
        viewModel.jList.getActionMap().put(COPY, copy);
        viewModel.jList.getActionMap().put(CUT, cut);
        viewModel.jList.getActionMap().put(RENAME, rename);
        viewModel.jList.getActionMap().put(PASTE, paste);
        viewModel.jList.getActionMap().put(DELETE, delete);
        viewModel.jList.getActionMap().put(PROPERTIES, properties);

    }

    public void executeCommand(String strAction) {

        if (strAction != null) {
            Cmd executingCommand = objInvoker.getCommand(strAction);
            executingCommand.execute(localeViewModel);
        }
    }

    public void hideOpenWithForFolder(ViewModel viewModel) {
        viewModel.menu.removeAll();
        viewModel.menu.add(copy);
        viewModel.menu.add(cut);
        viewModel.menu.add(rename);
        viewModel.menu.add(paste);
        viewModel.menu.add(delete);
        viewModel.menu.add(properties);
    }

    public void showOpenWithForFolder(ViewModel viewModel) {
        viewModel.menu.removeAll();
        viewModel.menu.add(openWith);
        viewModel.menu.add(copy);
        viewModel.menu.add(cut);
        viewModel.menu.add(rename);
        viewModel.menu.add(paste);
        viewModel.menu.add(delete);
        viewModel.menu.add(properties);        
    }
}
