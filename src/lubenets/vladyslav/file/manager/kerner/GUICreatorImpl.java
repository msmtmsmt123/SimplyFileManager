package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import lubenets.vladyslav.file.manager.last.command.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.last.command.FileAssosoationDetectorImpl;
import lubenets.vladyslav.file.manager.last.command.FilePropertiesDetectorImpl;
import lubenets.vladyslav.file.manager.navigation.ValueChanged;
import lubenets.vladyslav.file.manager.navigation.ValueChengedImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.CopyFile;
import lubenets.vladyslav.file.manager.right.mouse.menu.CopyFileImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.CutFile;
import lubenets.vladyslav.file.manager.right.mouse.menu.CutFileImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.DeleteFile;
import lubenets.vladyslav.file.manager.right.mouse.menu.DeleteFileImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.FilePropertiesDetector;
import lubenets.vladyslav.file.manager.right.mouse.menu.MouseClicked;
import lubenets.vladyslav.file.manager.right.mouse.menu.MouseClickedImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileModule;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileWith;
import lubenets.vladyslav.file.manager.right.mouse.menu.OpenFileWithImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.PasteFile;
import lubenets.vladyslav.file.manager.right.mouse.menu.PasteFileImpl;
import lubenets.vladyslav.file.manager.right.mouse.menu.RenameFile;
import lubenets.vladyslav.file.manager.right.mouse.menu.RenameFileImpl;

@SuppressWarnings("serial")
public class GUICreatorImpl extends JPanel implements ListSelectionListener, GUICreator {

    public static JList jList;
    public JScrollPane jscrlp;
    public JButton jBtnBye;
    public static JTextField jFilter;

    public boolean exitFlag = true;
    public static File[] fileList;
    public String[] data;
    public static String backStep;
    public static String path = "";
    public static DefaultListModel lm;
    public static FileAssosiationDetecter fad;
    public static FillList fl = new FillListImpl();
    public static FileManager fm;
    public static JFrame jFrm = new JFrame("Simple file manager");

    public static String buffer = "";
    public static boolean filesMustMove = false;

    private static final long serialVersionUID = 1L;
    private static final String ACTION_OPEN_WITH = "Open with...";
    private static final String COPY = "Copy";
    private static final String CUT = "Cut";
    private static final String PASTE = "Paste";
    private static final String RENAME = "Rename";
    private static final String DELETE = "Delete";
    private static final String PROPERTIES = "Properties";

    private JPopupMenu menu;
    public static boolean doubleClick = true;

    JPopupMenu jpu;

    GUICreatorImpl() {

        initActions();
        jpu = new JPopupMenu();
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

        fad = new FileAssosoationDetectorImpl();

        File file = new File(OpenFileModule.FILE_ASSOSIATION_CFG);
        ReadSettings rs = new ReadSettingsImpl();
        rs.readFromFile(file);

        lm = new DefaultListModel();
        fm = new FileManagerImpl();

        jFrm.getContentPane().setLayout(new FlowLayout());
        jFrm.getContentPane().setLayout(new BorderLayout());
        jFrm.setSize(1200, 700);
        jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartDataLoader startDataLoader = new StartDataLoaderImpl();
        startDataLoader.loadInformation(this);

        jFilter = new JTextField();
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

    }

    private void initActions() {
        
        menu = new JPopupMenu();

        Action openWith = new AbstractAction(ACTION_OPEN_WITH) {

            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e) {
                OpenFileWith ofw = new OpenFileWithImpl();
                ofw.openThis();
            }
        };
        Action copy = new AbstractAction(COPY) {

            public void actionPerformed(ActionEvent e) {
                CopyFile cf = new CopyFileImpl();
                cf.copyThis();
            }
        };
        Action cut = new AbstractAction(CUT) {

            public void actionPerformed(ActionEvent e) {
                CutFile cf = new CutFileImpl();
                cf.copyThis();
            }
        };
        Action paste = new AbstractAction(PASTE) {

            public void actionPerformed(ActionEvent e) {
                PasteFile pf = new PasteFileImpl();
                pf.pasteThis();

            }
        };
        Action rename = new AbstractAction(RENAME) {

            public void actionPerformed(ActionEvent e) {
                RenameFile rf = new RenameFileImpl();
                rf.renameThis();
            }
        };
        Action delete = new AbstractAction(DELETE) {

            public void actionPerformed(ActionEvent e) {
                DeleteFile dfi = new DeleteFileImpl();
                dfi.deleteThis();
            }
        };
        Action properties = new AbstractAction(PROPERTIES) {

            public void actionPerformed(ActionEvent e) {
                FilePropertiesDetector fpd = new FilePropertiesDetectorImpl();
                fpd.showProperties();
            }
        };

        getActionMap().put(ACTION_OPEN_WITH, openWith);
        menu.add(openWith);
        getActionMap().put(COPY, copy);
        menu.add(copy);
        getActionMap().put(CUT, cut);
        menu.add(cut);
        getActionMap().put(RENAME, rename);
        menu.add(rename);
        getActionMap().put(PASTE, paste);
        menu.add(paste);
        getActionMap().put(DELETE, delete);
        menu.add(delete);
        getActionMap().put(PROPERTIES, properties);
        menu.add(properties);

    }

    public void valueChanged(ListSelectionEvent e) {
        ValueChanged vc = new ValueChengedImpl();
        vc.changeFlag();
        if (doubleClick) {
            fl.fillData();
            doubleClick = false;
        } else {
        }

    }

    public static void main(String[] str) {
        new GUICreatorImpl();
    }

}
