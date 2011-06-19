package lubenets.vladyslav.file.manager.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.file.filter.FileFilter;
import lubenets.vladyslav.file.manager.right.mouse.menu.PopUpMenu;

public class ViewModel extends ApplicationModel implements ListSelectionListener {

    public JList jList;
    JScrollPane jscrlp;
    JButton jBtnBye;
    public JTextField jFilter;
    public JPopupMenu menu;
    JPopupMenu jpu;
    JFrame jFrm;
    PopUpMenu pom;
    public DefaultListModel lm;

    public boolean exitFlag;
    public boolean doubleClick;
    public Integer selectedIndex;

    public ViewModel(Application application) {
        super(application);
        menu = new JPopupMenu();
        lm = new DefaultListModel();
        showGUI();
    }

    public void showGUI() {

        exitFlag = true;
        doubleClick = true;

        pom = new PopUpMenu();
        jFrm = new JFrame("Simple file manager");
        jpu = new JPopupMenu();
        jFilter = new JTextField();

        jFrm.getContentPane().setLayout(new FlowLayout());
        jFrm.getContentPane().setLayout(new BorderLayout());
        jFrm.setSize(1200, 700);
        jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFilter.setText("Enter file name to filter");
        jFilter.selectAll();

        createFilterListener();

        jList = new JList(lm);

        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jscrlp = new JScrollPane(jList);
        jscrlp.setPreferredSize(new Dimension(400, 500));
        jList.addListSelectionListener(this);

        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                menu.show(e.getComponent(), e.getX(), e.getY());
            }

            public void mouseClicked(MouseEvent e) {
                getApplication().getFileModel().selectedIndex = jList.getSelectedIndex();
                if (e.getClickCount() == 2) {
                    getApplication().getController().setDataToListModelAfterSelection(lm);
                }
            }
        });

        jBtnBye = new JButton("Exit");

        jBtnBye.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exitFlag = false;
                jFrm.dispose();
            }
        });

        jFrm.getContentPane().add(jFilter, BorderLayout.BEFORE_FIRST_LINE);
        jFrm.getContentPane().add(jscrlp, FlowLayout.LEFT);
        jFrm.getContentPane().add(jBtnBye, BorderLayout.AFTER_LAST_LINE);

        jFrm.setVisible(true);

        final JMenuItem jmiOpen = new JMenuItem("Open with...");
        final JMenuItem jmiCopy = new JMenuItem("Copy");
        final JMenuItem jmiCut = new JMenuItem("Cut");
        final JMenuItem jmiPaste = new JMenuItem("Paste");
        final JMenuItem jmiRename = new JMenuItem("Rename");
        final JMenuItem jmiDelete = new JMenuItem("Delete");
        final JMenuItem jmiProperties = new JMenuItem("Properties");

        jpu.add(jmiOpen);
        jpu.add(jmiCopy);
        jpu.add(jmiCut);
        jpu.add(jmiPaste);
        jpu.add(jmiRename);
        jpu.add(jmiDelete);
        jpu.add(jmiProperties);

        pom.activate(this);
    }

    public void createFilterListener() {
        jFilter.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                FileFilter ff = new FileFilter();
                ff.filterThis(getApplication().getViewModel());
            }
        });

    }

    public DefaultListModel getListModel() {
        return lm;
    }

    public void setListModel(Map<Integer, String> modelOfTheList) {
        for (int i = 0; i < modelOfTheList.size(); i++) {
            this.lm.add(i, modelOfTheList.get(i));
        }
    }

    public Integer getSelectedIndex() {
        return jList.getSelectedIndex();
    }

    public void valueChanged(ListSelectionEvent e) {
    }


}
