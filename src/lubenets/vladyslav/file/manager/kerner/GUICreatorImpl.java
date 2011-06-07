package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.data.loader.FillList;
import lubenets.vladyslav.file.manager.data.loader.FillListImpl;
import lubenets.vladyslav.file.manager.data.loader.StartDataLoader;
import lubenets.vladyslav.file.manager.data.loader.StartDataLoaderImpl;
import lubenets.vladyslav.file.manager.file.filter.FileFilter;
import lubenets.vladyslav.file.manager.file.filter.FileFilterImpl;
import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.navigation.FileAssosoationDetectorImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.MouseClicked;
import lubenets.vladyslav.file.manager.right.mouse.menu.MouseClickedImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileModule;
import lubenets.vladyslav.file.manager.right.mouse.menu.RightMouseMenu;
import lubenets.vladyslav.file.manager.right.mouse.menu.RightMouseMenuImpl;

public class GUICreatorImpl extends JPanel implements ListSelectionListener, GUICreator {

    // Components
    public static JList jList;
    public static JScrollPane jscrlp;
    public static JButton jBtnBye;
    public static JTextField jFilter;
    public static JPopupMenu menu;
    public static JPopupMenu jpu;
    public static DefaultListModel lm;
    public static FileAssosiationDetecter fad;
    public static FillList fl;
    public static FileManager fm;
    public static JFrame jFrm;
    public static RightMouseMenu rmm;
    public static File file;
    public static ReadSettings rs;
    public static StartDataLoader startDataLoader;

    // Constants
    private static final long serialVersionUID = 1L;
    public static final String ACTION_OPEN_WITH = "Open with...";
    public static final String COPY = "Copy";
    public static final String CUT = "Cut";
    public static final String PASTE = "Paste";
    public static final String RENAME = "Rename";
    public static final String DELETE = "Delete";
    public static final String PROPERTIES = "Properties";

    // Variables
    public static boolean doubleClick = true;
    public static boolean exitFlag = true;
    public static File[] fileList;
    public static String[] data;
    public static String backStep;
    public static String path = "";
    public static String buffer = "";
    public static boolean filesMustMove = false;

    GUICreatorImpl() {

        rmm = new RightMouseMenuImpl();
        fl = new FillListImpl();
        jFrm = new JFrame("Simple file manager");
        jpu = new JPopupMenu();
        file = new File(OpenFileModule.FILE_ASSOSIATION_CFG);
        rs = new ReadSettingsImpl();
        fad = new FileAssosoationDetectorImpl();
        lm = new DefaultListModel();
        fm = new FileManagerImpl();
        startDataLoader = new StartDataLoaderImpl();
        jFilter = new JTextField();

        rs.readFromFile(file);

        jFrm.getContentPane().setLayout(new FlowLayout());
        jFrm.getContentPane().setLayout(new BorderLayout());
        jFrm.setSize(1200, 700);
        jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startDataLoader.loadInformation(this);

        jFilter.setText("Enter file name to filter");
        jFilter.selectAll();

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
                FileFilter ff = new FileFilterImpl();
                ff.filterThis();
            }
        });

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
                MouseClicked mc = new MouseClickedImpl();
                mc.doAction(e);
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

        rmm.activate();

    }

    public void valueChanged(ListSelectionEvent e) {

    }

    public static void main(String[] str) {
        new GUICreatorImpl();
    }

}
