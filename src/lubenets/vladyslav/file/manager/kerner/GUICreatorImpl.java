package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.labels.RedLabelFileViewFactory;
import lubenets.vladyslav.file.manager.last.command.FileAssosiationDetecter;
import lubenets.vladyslav.file.manager.last.command.FileAssosoationDetectorImpl;
import lubenets.vladyslav.file.manager.open.file.OpenFileModule;
import lubenets.vladyslav.file.manager.open.file.OpenFileModuleImpl;
import lubenets.vladyslav.file.manager.start.data.loader.StartDataLoader;
import lubenets.vladyslav.file.manager.start.data.loader.StartDataLoaderImpl;

public class GUICreatorImpl implements GUICreator {

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

	GUICreatorImpl() {
		fad = new FileAssosoationDetectorImpl();
		
		File file = new File(OpenFileModule.FILE_ASSOSIATION_CFG);

		if (file.exists()) {
			try {

				FileInputStream fis = new FileInputStream(OpenFileModule.FILE_ASSOSIATION_CFG);
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
		final FileManager fm = new FileManagerImpl();
		final JFrame jFrm = new JFrame("Simple file manager");

		jFrm.getContentPane().setLayout(new FlowLayout());
		jFrm.getContentPane().setLayout(new BorderLayout());
		jFrm.setSize(1200, 700);
		jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		StartDataLoader startDataLoader = new StartDataLoaderImpl();
		startDataLoader.loadInformation(this);

		jList = new JList(lm);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jscrlp = new JScrollPane(jList);
		jscrlp.setPreferredSize(new Dimension(700, 500));

		jList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

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
						String afterAnalysis = analysis.substring(
								indexNumber + 5, analysis.length() - 1);

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

}
