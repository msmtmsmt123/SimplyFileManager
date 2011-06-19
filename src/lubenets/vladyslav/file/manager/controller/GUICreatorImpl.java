package lubenets.vladyslav.file.manager.controller;

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
import lubenets.vladyslav.file.manager.file.filter.FileFilter;
import lubenets.vladyslav.file.manager.kernel.FileManager;
import lubenets.vladyslav.file.manager.kernel.FileManagerImpl;
import lubenets.vladyslav.file.manager.kernel.ReadAndWriteSettings;
import lubenets.vladyslav.file.manager.kernel.ReadAndWriteSettingsImpl;
import lubenets.vladyslav.file.manager.navigation.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.navigation.FileAssosoationDetectorImpl;
import lubenets.vladyslav.file.manager.navigation.MouseClicked;
import lubenets.vladyslav.file.manager.navigation.MouseClicked;
import lubenets.vladyslav.file.manager.right.mouse.menu.PopUpMenu;

public class GUICreatorImpl extends JPanel implements ListSelectionListener, GUICreator {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Components
    public static  JList jList;
    public  JScrollPane jscrlp;
    public  JButton jBtnBye;
    public  JTextField jFilter;
    public static  JPopupMenu menu;
    public  JPopupMenu jpu;
    public static  DefaultListModel lm;
    public  FileAssosiationDetecter fad;
    public  FillList fl;
    public  FileManager fm;
    public  JFrame jFrm;
    public  PopUpMenu pom;
    public  File file;
    public  ReadAndWriteSettings rs;

    public  final String ACTION_OPEN_WITH = "Open with...";
    public  final String COPY = "Copy";
    public  final String CUT = "Cut";
    public  final String PASTE = "Paste";
    public  final String RENAME = "Rename";
    public  final String DELETE = "Delete";
    public  final String PROPERTIES = "Properties";
    public  final String FILE_ASSOSIATION_CFG = "ucf";

    // Variables
    public  boolean doubleClick = true;
    public  boolean exitFlag = true;
    public  File[] fileList;
    public  String[] data;
    public  String backStep;
    public  String path = "";
    public  String buffer = "";

    GUICreatorImpl() {

        pom = new PopUpMenu();
        fl = new FillList();
        jFrm = new JFrame("Simple file manager");
        jpu = new JPopupMenu();
        file = new File(FILE_ASSOSIATION_CFG);
        rs = new ReadAndWriteSettingsImpl();
        fad = new FileAssosoationDetectorImpl();
        lm = new DefaultListModel();
        fm = new FileManagerImpl();
        jFilter = new JTextField();

        rs.readFromFile(file);

        jFrm.getContentPane().setLayout(new FlowLayout());
        jFrm.getContentPane().setLayout(new BorderLayout());
        jFrm.setSize(1200, 700);
        jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



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
                FileFilter ff = new FileFilter();
                ff.filterThis();
            }
        });


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
                MouseClicked mc = new MouseClicked();
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

        pom.activate();

    }

    public void valueChanged(ListSelectionEvent e) {

    }

    public  void main(String[] str) {
        new GUICreatorImpl();
    }

}
