package lubenets.vladyslav.file.manager.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    JMenuItem jmiOpen;
    JScrollPane jscrlp;
    JButton jBtnBye;
    public JTextField jFilter;
    public JPopupMenu menu;
    JFrame jFrm;
    public PopUpMenu pom;
    public DefaultListModel lm;
    FileFilter ff = new FileFilter();

    public boolean exitFlag;
    public boolean doubleClick;
    public Integer selectedIndex;
    private Point framePosition = new Point();
    private Dimension frameSize = new Dimension();

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
        jFilter = new JTextField();

        jFrm.getContentPane().setLayout(new FlowLayout());
        jFrm.getContentPane().setLayout(new BorderLayout());
        jFrm.setSize(1200, 700);
        jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        framePosition = getApplication().getSettingsModel().getFramePosition();
        frameSize = getApplication().getSettingsModel().getFrameSize();
        if (framePosition != null && frameSize != null) {
            jFrm.setLocation(framePosition);
            jFrm.setSize(frameSize);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                framePosition = jFrm.getLocation();
                frameSize = jFrm.getSize();
                getApplication().getSettingsModel().setFramePosition(framePosition);
                getApplication().getSettingsModel().setFrameSize(frameSize);
            }
        }));
        
        
        jFrm.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            }
        });

        jFilter.setText("Enter file name to filter");
        jFilter.selectAll();

        createFilterListener();

        jList = new JList(lm);

        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jscrlp = new JScrollPane(jList);
//        jscrlp.setPreferredSize(new Dimension(400, 500));
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
                } else {
                    getApplication().getController().setPopMenu();
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
                getApplication().getController().filterActivated1 = true;
                ff.filterThis(getApplication().getViewModel());

            }
        });

    }

    public DefaultListModel getListModel() {
        return lm;
    }

    public void setListModel(Map<Integer, String> modelOfTheList) {
        this.lm.clear();
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
