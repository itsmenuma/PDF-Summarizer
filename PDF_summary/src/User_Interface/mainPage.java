package User_Interface;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class mainPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private File selectedFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPage frame = new mainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainPage() {
		setTitle("PDF_Summarizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 407, 539);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("PDF Summarizer");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
		lblNewLabel.setBounds(105, 32, 185, 56);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Choose file type");
		lblNewLabel_1.setBounds(25, 139, 85, 17);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Selected File:");
		lblNewLabel_2.setBounds(25, 200, 85, 14);
		contentPane.add(lblNewLabel_2);

		// Label to display the selected file path
		JLabel filePathLabel = new JLabel("");
		filePathLabel.setBounds(122, 200, 250, 14);
		contentPane.add(filePathLabel);

		// File chooser button
		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.setBounds(122, 230, 120, 23);
		btnChooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					filePathLabel.setText(selectedFile.getAbsolutePath());
				}
			}
		});
		contentPane.add(btnChooseFile);

		JLabel lblNewLabel_3 = new JLabel("Choose level");
		lblNewLabel_3.setBounds(25, 300, 85, 18);
		contentPane.add(lblNewLabel_3);

		// Level dropdown
		JComboBox<String> comboBoxLevel = new JComboBox<>();
		comboBoxLevel.setModel(new DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High" }));
		comboBoxLevel.setBounds(122, 300, 81, 22);
		contentPane.add(comboBoxLevel);

		// File type dropdown
		JComboBox<String> comboBoxFileType = new JComboBox<>();
		comboBoxFileType.setModel(new DefaultComboBoxModel<>(new String[] { "PDF", "DOC", "DOCX" }));
		comboBoxFileType.setBounds(122, 135, 81, 24);
		contentPane.add(comboBoxFileType);

		// Summarize button (currently checks the file type)
		JButton btnSummarize = new JButton("Summarize");
		btnSummarize.setBounds(122, 400, 120, 23);
		btnSummarize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFile != null) {
					String filePath = selectedFile.getAbsolutePath();
					String selectedFileType = (String) comboBoxFileType.getSelectedItem();
					String fileExtension = getFileExtension(selectedFile);

					if (fileMatchesSelectedType(fileExtension, selectedFileType)) {
						// Proceed with summarizing
						String level = (String) comboBoxLevel.getSelectedItem();
						System.out.println("File Path: " + filePath);
						System.out.println("File Type: " + selectedFileType);
						System.out.println("Summarization Level: " + level);
					} else {
						// Display error message
						JOptionPane.showMessageDialog(null, "File type doesn't match selected input! \nPlease select a " 
							+ selectedFileType + " file.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "No file selected!");
				}
			}
		});
		contentPane.add(btnSummarize);
	}

	// Helper method to get the file extension
	private String getFileExtension(File file) {
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex + 1).toLowerCase(); // Return file extension in lowercase
		}
		return "";
	}

	// Method to check if the file extension matches the selected file type
	private boolean fileMatchesSelectedType(String fileExtension, String selectedFileType) {
		switch (selectedFileType) {
			case "PDF":
				return fileExtension.equals("pdf");
			case "DOC":
				return fileExtension.equals("doc");
			case "DOCX":
				return fileExtension.equals("docx");
			default:
				return false;
		}
	}
}
