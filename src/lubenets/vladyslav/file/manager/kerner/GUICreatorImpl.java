package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.copy.files.CopyFiles;
import lubenets.vladyslav.file.manager.copy.files.CopyFilesImpl;
import lubenets.vladyslav.file.manager.delete.files.DeleteFiles;
import lubenets.vladyslav.file.manager.delete.files.DeleteFilesImpl;
import lubenets.vladyslav.file.manager.labels.RedLabelFileViewFactory;
import lubenets.vladyslav.file.manager.last.command.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.last.command.FileAssosoationDetectorImpl;
import lubenets.vladyslav.file.manager.open.file.OpenFileModule;
import lubenets.vladyslav.file.manager.open.file.OpenFileModuleImpl;
import lubenets.vladyslav.file.manager.start.data.loader.StartDataLoader;
import lubenets.vladyslav.file.manager.start.data.loader.StartDataLoaderImpl;

@SuppressWarnings("serial")
public class GUICreatorImpl extends JPanel implements ListSelectionListener,
		GUICreator {

	public JList jList;
	public JScrollPane jscrlp;
	public JButton jBtnBye;
	public JTextField jFilter;

	public boolean exitFlag = true;
	public File[] fileList;
	public String[] data;
	public String backStep;
	public String path = "";
	public DefaultListModel lm;
	public FileAssosiationDetecter fad;
	DataToShare dataToShare;
	public FileManager fm;
	public static JFrame jFrm = new JFrame("Simple file manager");

	public String buffer = "";
	public boolean filesMustMove = false;

	private static final long serialVersionUID = 1L;
	private static final String ACTION_OPEN_WITH = "Open with...";
	private static final String COPY = "Copy";
	private static final String CUT = "Cut";
	private static final String PASTE = "Paste";
	private static final String RENAME = "Rename";
	private static final String DELETE = "Delete";
	private static final String PROPERTIES = "Properties";

	private JPopupMenu menu;
	public boolean doubleClick = true;

	// public RightMtoDeleteouseMenu rmm;
	JPopupMenu jpu;

	GUICreatorImpl() {

		initActions();
		jpu = new JPopupMenu();
		dataToShare = new DataToShareImpl();
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

		if (file.exists()) {
			try {

				FileInputStream fis = new FileInputStream(
						OpenFileModule.FILE_ASSOSIATION_CFG);
				ObjectInputStream ois = new ObjectInputStream(fis);
				fad = (FileAssosiationDetecter) ois.readObject();
				ois.close();

			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(jFrm, "File not found!");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(jFrm, "Input-output error!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(jFrm, "System error occured!");
				e.printStackTrace();
			}
		}

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
				String regexp = "";
				String input = "";

				input = jFilter.getText();
				regexp = input;
				Pattern p = Pattern.compile(regexp);

				SortedSet<String> folders = new TreeSet<String>();
				SortedSet<String> files = new TreeSet<String>();

				File fileType = new File(path);
				File[] newFileList = fileType.listFiles();
				for (int k = 0; k < newFileList.length; k++) {

					if (newFileList[k].isFile()) {
						Matcher m = p.matcher(newFileList[k].getAbsolutePath());
						if (m.find()) {
							if (m.start() == 1) {
								files.add(newFileList[k].getName());
							}
						}
					} else {
						Matcher m = p.matcher(newFileList[k].getAbsolutePath());
						if (m.find()) {
							if (m.start() == 1) {
								folders.add(newFileList[k].getName());
							}
						}
					}

				}

				Iterator<String> iteratorForFolders = folders.iterator();
				Iterator<String> iteratorForFiles = files.iterator();

				lm.clear();
				lm.addElement("..");

				for (int i = 0; i < folders.size(); i++) {
					if (iteratorForFolders.hasNext()) {
						lm.addElement(iteratorForFolders.next());
					}
				}

				for (int i = 0; i < files.size(); i++) {
					if (iteratorForFiles.hasNext()) {
						lm.addElement(iteratorForFiles.next());
					}
				}

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
				if (e.isPopupTrigger())
					showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				menu.show(e.getComponent(), e.getX(), e.getY());
			}

			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getClickCount());
				if (e.getClickCount() != 2) {
					doubleClick = false;
					return;
				}
				if (e.getClickCount() == 2) {
					doubleClick = true;
					fillData();

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

	}

	private void initActions() {
		menu = new JPopupMenu();
		Action openWith = new AbstractAction(ACTION_OPEN_WITH) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();

				File fileType = new File(path + File.separator + value);
				if (fileType.isFile()) {
					String fileToOpen = path + File.separator + value;

					OpenFileModule openFile = new OpenFileModuleImpl();
					openFile.openThis(fad, fileToOpen, value);
				} else
					fillData();

			}
		};
		Action copy = new AbstractAction(COPY) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);
				buffer = source.getAbsolutePath();
				filesMustMove = false;
			}
		};
		Action cut = new AbstractAction(CUT) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);
				buffer = source.getAbsolutePath();
				filesMustMove = true;
			}
		};
		Action paste = new AbstractAction(PASTE) {

			@Override
			public void actionPerformed(ActionEvent e) {
				File destination;
				String value = (String) jList.getSelectedValue();
				if (value == null || (value.equals(".."))) {
					destination = new File(path);
				} else
					destination = new File(path + File.separator + value);

				CopyFiles cf = new CopyFilesImpl();
				cf.copingFiles(buffer, destination.getAbsolutePath());
				if (filesMustMove) {
					File toDelete = new File(buffer);
					DeleteFiles df = new DeleteFilesImpl();
					df.removeFiles(toDelete);
				}
				fillData();
			}
		};
		Action rename = new AbstractAction(RENAME) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);
				File response = new File(path + File.separator
						+ JOptionPane.showInputDialog("Enter a new file name"));
				if (!source.renameTo(response)) {
					JOptionPane.showMessageDialog(jFrm, "I can`t rename this!");
				}
				showData(path);
			}
		};
		Action delete = new AbstractAction(DELETE) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);
				int response = JOptionPane
						.showConfirmDialog(jFrm, "Remove objects?", "Delete?",
								JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION: {
					DeleteFiles df = new DeleteFilesImpl();
					df.removeFiles(source);
					showData(path);
				}
					break;
				case JOptionPane.NO_OPTION:
					break;
				}
			}
		};
		Action properties = new AbstractAction(PROPERTIES) {

			@Override
			public void actionPerformed(ActionEvent e) {

				JList propertiesList;
				JScrollPane scrollPaneForList;

				String file = null;
				String fileSize = null;
				String parent;
				String hidden;
				String dateOfTheLastModified;
				String writeReadPermission;

				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);

				if (source.isDirectory()) {
					file = "Directory" + source;
					fileSize = "Folder size is " + source.length() + " byte(s)";
				} else {
					file = "Unknown file type";
					fileSize = "Unknuown size";
				}

				if (source.isFile()) {
					file = "File" + source;
					fileSize = "File size is " + source.length() + " byte(s)";
				} else {
					file = "Unknown file type";
					fileSize = "Unknuown";
				}

				if (source.canWrite()) {
					if (source.canRead())
						writeReadPermission = source.getPath()
								+ " is read-write";
					else
						writeReadPermission = source.getPath()
								+ " is write only";
				} else {
					if (source.canRead())
						writeReadPermission = source.getPath()
								+ " is read only";
					else
						writeReadPermission = "Permission to "
								+ source.getPath() + " denied";
				}

				if (source.getParent() == null) {
					parent = source.getPath() + " is a root directory.";
				} else {
					parent = "Parent of " + source.getPath() + " is "
							+ source.getParent() + ".";
				}

				if (source.isHidden()) {
					hidden = source.getPath() + " is Hidden.";
				} else
					hidden = source.getPath() + " is not Hidden.";

				dateOfTheLastModified = source.getPath()
						+ " was last modified on "
						+ new java.util.Date(source.lastModified());

				String[] infoForList = { file, fileSize, parent, hidden,
						dateOfTheLastModified, writeReadPermission };

				propertiesList = new JList(infoForList);
				scrollPaneForList = new JScrollPane(propertiesList);
				scrollPaneForList.setPreferredSize(new Dimension(120, 70));

				JFrame frameForProperties = new JFrame("Properties");
				frameForProperties.setSize(600, 150);

				frameForProperties.getContentPane().add(scrollPaneForList);
				frameForProperties.setVisible(true);
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (doubleClick) {
			fillData();
			doubleClick = false;
		} else {
		}

	}

	private void fillData() {

		final ListForModel modelList = new ListForModel();
		modelList.setFactory(new RedLabelFileViewFactory());

		ListModelFilling listModelFilling = new ListModelFillingImpl();
		listModelFilling.getData(this);

		SortedSet<String> folders = new TreeSet<String>();
		SortedSet<String> files = new TreeSet<String>();

		int idx = jList.getSelectedIndex();

		if (idx == -1) {
			return;
		}

		Object value = jList.getSelectedValues()[0];
		String fullPath = path + File.separator + value.toString();
		if (value.toString().equals("..")) {
			fullPath = value.toString();
		}

		if (!fullPath.equals("..")) {
			backStep = (String) fullPath;
		}

		fileList = fm.createFileList((String) fullPath);

		if (idx == 0) {
			if (!fullPath.equals(File.separator)) {
				int decPosition = backStep.lastIndexOf(File.separator);
				if (decPosition == 0) {
					backStep = backStep.substring(0, decPosition + 1);
				} else
					backStep = backStep.substring(0, decPosition);

				fileList = fm.createFileList(backStep);
			}
		}

		DefaultListModel lm = (DefaultListModel) jList.getModel();

		if (fileList != null) {
			lm.clear();
			lm.addElement("..");
		}

		if (fileList != null) {

			for (int i = 0; i < fileList.length; i++) {
				modelList.putElement(fileList[i].getAbsoluteFile());
			}

			for (int j = 0; j < modelList.getSize(); j++) {
				String analysis = modelList.getElementAt(j).toString();
				int indexNumber = analysis.indexOf("text=");
				String afterAnalysis = analysis.substring(indexNumber + 5,
						analysis.length() - 1);

				path = afterAnalysis.substring(0,
						afterAnalysis.lastIndexOf(File.separator));

				if (afterAnalysis.lastIndexOf(File.separator) == 0) {
					path = File.separator;
				}

				jFrm.setTitle(path);

				String output = afterAnalysis.substring(
						afterAnalysis.lastIndexOf(File.separator) + 1,
						afterAnalysis.length());

				File fileType = new File(path + File.separator + output);
				if (fileType.isFile()) {
					files.add(output);
				} else
					folders.add(output);

			}

			Iterator<String> iteratorForFolders = folders.iterator();
			Iterator<String> iteratorForFiles = files.iterator();

			for (int i = 0; i < folders.size(); i++) {
				if (iteratorForFolders.hasNext()) {
					lm.addElement(iteratorForFolders.next());
				}
			}

			for (int i = 0; i < files.size(); i++) {
				if (iteratorForFiles.hasNext()) {
					lm.addElement(iteratorForFiles.next());
				}
			}
		}

		if (fileList == null) {

			String fileToOpen = path + File.separator + value;

			OpenFileModule openFile = new OpenFileModuleImpl();
			openFile.openThis(fad, fileToOpen, value);

		}

	}

	private void showData(String newPath) {

		SortedSet<String> folders = new TreeSet<String>();
		SortedSet<String> files = new TreeSet<String>();

		File fileType = new File(newPath);
		File[] newFileList = fileType.listFiles();
		for (int k = 0; k < newFileList.length; k++) {
			if (newFileList[k].isFile()) {
				files.add(newFileList[k].getName());
			} else
				folders.add(newFileList[k].getName());
		}

		Iterator<String> iteratorForFolders = folders.iterator();
		Iterator<String> iteratorForFiles = files.iterator();

		lm.clear();
		lm.addElement("..");

		for (int i = 0; i < folders.size(); i++) {
			if (iteratorForFolders.hasNext()) {
				lm.addElement(iteratorForFolders.next());
			}
		}

		for (int i = 0; i < files.size(); i++) {
			if (iteratorForFiles.hasNext()) {
				lm.addElement(iteratorForFiles.next());
			}
		}

	}

	public static void main(String[] str) {
		new GUICreatorImpl();
	}

}
