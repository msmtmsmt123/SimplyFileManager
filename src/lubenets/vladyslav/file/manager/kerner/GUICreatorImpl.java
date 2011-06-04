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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.copy.files.CopyFiles;
import lubenets.vladyslav.file.manager.copy.files.CopyFilesImpl;
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
	public JLabel jLable;
	public JScrollPane jscrlp;
	public JButton jBtnBye;

	public boolean exitFlag = true;
	public File[] fileList;
	public String[] data;
	public String backStep;
	public String path = "";
	public DefaultListModel lm;
	public FileAssosiationDetecter fad;
	DataToShare dataToShare = new DataToShareImpl();
	public FileManager fm;
	public JFrame jFrm;

	private static final long serialVersionUID = 1L;
	private static final String ACTION_OPEN_WITH = "Open with...";
	private static final String COPY = "Copy";
	private static final String RENAME = "Rename/move";
	private static final String DELETE = "Delete";
	private static final String PROPERTIES = "Properties";

	private JPopupMenu menu;
	public boolean doubleClick = true;

	// public RightMouseMenu rmm;
	JPopupMenu jpu;

	GUICreatorImpl() {

		jFrm = new JFrame("Simple file manager");
		initActions();
		jpu = new JPopupMenu();
		final JMenuItem jmiOpen = new JMenuItem("Open with...");
		final JMenuItem jmiCopy = new JMenuItem("Copy");
		final JMenuItem jmiRenameMove = new JMenuItem("Rename/Move");
		final JMenuItem jmiDelete = new JMenuItem("Delete");
		final JMenuItem jmiProperties = new JMenuItem("Properties");

		jpu.add(jmiOpen);
		jpu.add(jmiCopy);
		jpu.add(jmiRenameMove);
		jpu.add(jmiDelete);
		jpu.add(jmiProperties);

		// rmm = new RightMouseMenuImpl();
		// rmm.activate(jList);

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
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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

		jList = new JList(lm);
		jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jscrlp = new JScrollPane(jList);
		jscrlp.setPreferredSize(new Dimension(700, 500));

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
				// System.out.println(doubleClick);
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
				System.exit(0);
			}
		});

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
				String destination = JOptionPane
						.showInputDialog("Enter a path to copy file/folder");
				CopyFiles cf = new CopyFilesImpl();
				cf.copingFiles(source.getAbsolutePath(), destination);
			}
		};
		Action rename = new AbstractAction(RENAME) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				JOptionPane.showMessageDialog(GUICreatorImpl.this,
						"Rename/move " + value);

			}
		};
		Action delete = new AbstractAction(DELETE) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				JOptionPane.showMessageDialog(GUICreatorImpl.this, "Delete "
						+ value);

			}
		};
		Action properties = new AbstractAction(PROPERTIES) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) jList.getSelectedValue();
				File source = new File(path + File.separator + value);
				
				JOptionPane.showMessageDialog(GUICreatorImpl.this,
						"Properties " + value);

			}
		};

		getActionMap().put(ACTION_OPEN_WITH, openWith);
		menu.add(openWith);
		getActionMap().put(COPY, copy);
		menu.add(copy);
		getActionMap().put(RENAME, rename);
		menu.add(rename);
		getActionMap().put(DELETE, delete);
		menu.add(delete);
		getActionMap().put(PROPERTIES, properties);
		menu.add(properties);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		// System.out.println("selectedListner " + doubleClick);
		if (doubleClick) {
			fillData();
			doubleClick = false;
		} else {
			// System.out.println("Inside");
			// jList.setSelectedIndex(-1);
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

				// System.out.println(backStep);
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




}
