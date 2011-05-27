package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lubenets.vladyslav.file.manager.labels.RedLabelFileViewFactory;

public class GUICreatorImpl implements GUICreator {

	JList jList;
	JLabel jLable;
	JScrollPane jscrlp;
	JButton jBtnBye;

	boolean exitFlag = true;
	File[] fileList;
	final String[] data;
	String backStep;
	String path = "";

//	private static final String REGEXP_FOR_FILE_DETECTING = "[*]{0,}\\.[*]{0,}";

	GUICreatorImpl() {

		fileList = File.listRoots();
		final FileManager fm = new FileManagerImpl();
		final JFrame jFrm = new JFrame("Simple file manager");

		jFrm.getContentPane().setLayout(new FlowLayout());
		jFrm.getContentPane().setLayout(new BorderLayout());
		jFrm.setSize(300, 200);
		jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final DefaultListModel lm = new DefaultListModel();

		data = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			data[i] = fileList[i].getPath();
			lm.add(i, data[i]);
		}

		jList = new JList(lm);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jscrlp = new JScrollPane(jList);
		jscrlp.setPreferredSize(new Dimension(300, 200));

		jList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				final ListForModel modelList = new ListForModel();
				modelList.setFactory(new RedLabelFileViewFactory());

				// Vector<Object> valueClone = new Vector<Object>();
				SortedSet<String> folders = new TreeSet<String>();
				SortedSet<String> files = new TreeSet<String>();

				int idx = jList.getSelectedIndex();

				if (idx == -1) {
					return;
				}

				Object value = jList.getSelectedValues()[0];
				String fullPath = path + "/" + value.toString();
				if (value.toString().equals("..")) {
					fullPath = value.toString();
				}

				if (!fullPath.equals("..")) {
					backStep = (String) fullPath;
				}

				fileList = fm.createFileList((String) fullPath);

				if (idx == 0) {
					if (!fullPath.equals("/")) {
						int decPosition = backStep.lastIndexOf("/");
						if (decPosition == 0) {
							backStep = backStep.substring(0, decPosition + 1);
						} else
							backStep = backStep.substring(0, decPosition);

						System.out.println(backStep);
						fileList = fm.createFileList(backStep);
					}
				}

				DefaultListModel lm = (DefaultListModel) jList.getModel();
				lm.clear();

				lm.addElement("..");

				if (fileList == null) {
					JOptionPane.showMessageDialog(jFrm, "Access denied!");
					return;
				}

				for (int i = 0; i < fileList.length; i++) {
					modelList.putElement(fileList[i].getAbsoluteFile());
				}

				for (int j = 0; j < modelList.getSize(); j++) {
					String analysis = modelList.getElementAt(j).toString();
					int indexNumber = analysis.indexOf("text=");
					String afterAnalysis = analysis.substring(indexNumber + 5,
							analysis.length() - 1);

					path = afterAnalysis.substring(0,
							afterAnalysis.lastIndexOf('/'));

					if (afterAnalysis.lastIndexOf('/') == 0) {
						path = "/";
					}

					jFrm.setTitle(path);

					String output = afterAnalysis.substring(
							afterAnalysis.lastIndexOf('/') + 1,
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
