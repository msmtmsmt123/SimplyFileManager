package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

public class FileManagerKernerEnterPoint {

	JList jList;
	JLabel jLable;
	JScrollPane jscrlp;
	JButton jBtnBye;

	boolean exitFlag = true;
	File[] fileList;
	final String[] data;

	FileManagerKernerEnterPoint() {


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

				Object value[] = jList.getSelectedValues();

				int idx = jList.getSelectedIndex();

				if (idx == -1) {
					return;
				}

				fileList = fm.createFileList((String) value[0]);

				if (idx == 0) {
					if (!value[0].equals("/")) {
						int decPosition = value[0].toString().lastIndexOf("/");
						System.out.println(value[0].toString().substring(0, decPosition));
						fileList = fm.createFileList(value[0].toString().substring(0, decPosition));
					}
				}
				
				DefaultListModel lm = (DefaultListModel) jList.getModel();
				System.out.println(lm.getSize());
				lm.clear();
				System.out.println(lm.getSize());

				lm.addElement("..");
				
				if (fileList==null) {
					JOptionPane.showMessageDialog(jFrm, "Access denied!");
					return;
				}
				
				for (int i = 0; i < fileList.length - 1; i++) {
					modelList.putElement(fileList[i].getAbsoluteFile());
				}


				for (int j = 0; j < modelList.getSize() - 1; j++) {
					String analysis = modelList.getElementAt(j).toString();
					int indexNumber = analysis.indexOf("text=");
					String afterAnalysis = analysis.substring(indexNumber + 5,
							analysis.length() - 1);

					System.out.println(afterAnalysis);
					lm.addElement(afterAnalysis);
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

	public static void main(String[] str) {
		new FileManagerKernerEnterPoint();
	}

}
