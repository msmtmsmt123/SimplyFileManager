package lubenets.vladyslav.file.manager.kerner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileManagerKernerEnterPoint {

	JList jList;
	JLabel jLable;
	JScrollPane jscrlp;
	JButton jBtnBuy;

	boolean exitFlag = true;
	File[] fileList;
	final String[] data;

	FileManagerKernerEnterPoint() {

		final ListForModel modelList = new ListForModel();
		fileList = File.listRoots();
		final FileManager fm = new FileManagerImpl();
		final JFrame jFrm = new JFrame("Simple file manager");
		jFrm.getContentPane().setLayout(new FlowLayout());
		jFrm.getContentPane().setLayout(new BorderLayout());
		jFrm.setSize(300, 200);
		jFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		data = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			data[i] = fileList[i].getPath();
		}

		jList = new JList(data);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jscrlp = new JScrollPane(jList);
		jscrlp.setPreferredSize(new Dimension(120, 90));
		jLable = new JLabel("Select an item");

		jList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int idx = 0;
				idx = jList.getSelectedIndex();
				if (idx == -1) {
					return;
				}

				fileList = fm.createFileList((String) data[idx]);
				for (int i = 0; i < fileList.length - 1; i++) {
					modelList.putElement(fileList[i].getAbsoluteFile());
					// System.out.println(fileList[i].getPath());
				}

				jList.removeAll();
				for (int j = 0; j < modelList.getSize() - 1; j++) {
					jList.add(modelList.getElementAt(j));
					System.out.println(modelList.getElementAt(j));
				}

				// jList = new JList(data);
				// jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				// jscrlp = new JScrollPane(jList);
				// jFrm.getContentPane().add(jscrlp, FlowLayout.LEFT);

			}
		});

		jBtnBuy = new JButton("Exit");

		jBtnBuy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				exitFlag = false;
				System.exit(0);
			}
		});

		jFrm.getContentPane().add(jscrlp, FlowLayout.LEFT);
		jFrm.getContentPane().add(jBtnBuy, BorderLayout.AFTER_LAST_LINE);

		jFrm.setVisible(true);

	}

	public static void main(String[] str) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FileManagerKernerEnterPoint();
			}
		});
	}

}
