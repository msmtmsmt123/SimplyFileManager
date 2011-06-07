package lubenets.vladyslav.file.manager.right.mouse.menu;

import java.awt.event.MouseEvent;

import lubenets.vladyslav.file.manager.kerner.GUICreatorImpl;

public class MouseClickedImpl implements MouseClicked {

    public void doAction(MouseEvent e) {
        System.out.println(e.getClickCount());
        if (e.getClickCount() != 2) {
            GUICreatorImpl.doubleClick = false;
            return;
        }
        if (e.getClickCount() == 2) {
            GUICreatorImpl.doubleClick = true;
            GUICreatorImpl.fl.fillData();

        }
        
    }



}
