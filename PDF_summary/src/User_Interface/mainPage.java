package User_Interface;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import Backend.TextSummarizer;
import Main.FileExtractor;

public class mainPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnSummarize;
    @SuppressWarnings("unused")
	private JTextField textField_1;
    private JComboBox<String> comboBoxFileType;
    private JComboBox<String> comboBoxSummarizationLevel;
    private File selectedFile;

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

    public mainPage() {
        setTitle("PDF Summarizer");
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

        JLabel filePathLabel = new JLabel("");
        filePathLabel.setBounds(122, 200, 250, 14);
        contentPane.add(filePathLabel);

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

        comboBoxSummarizationLevel = new JComboBox<>();
        comboBoxSummarizationLevel.setModel(new DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High" }));
        comboBoxSummarizationLevel.setBounds(122, 300, 81, 22);
        contentPane.add(comboBoxSummarizationLevel);

        comboBoxFileType = new JComboBox<>();
        comboBoxFileType.setModel(new DefaultComboBoxModel<>(new String[] { "PDF", "DOC", "DOCX" }));
        comboBoxFileType.setBounds(122, 135, 81, 24);
        contentPane.add(comboBoxFileType);

        setupSummarizeButton();
    }

    private void setupSummarizeButton() {
        btnSummarize = new JButton("Summarize");
        btnSummarize.setBounds(122, 400, 120, 23);
        btnSummarize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    String selectedFileType = (String) comboBoxFileType.getSelectedItem();
                    String summarizationLevel = (String) comboBoxSummarizationLevel.getSelectedItem();
                    String text = FileExtractor.extractText(selectedFile, selectedFileType);

                    if (text != null) {
                        String summary = TextSummarizer.summarize(text, summarizationLevel);
                        saveSummaryToWordDoc(summary);
                        JOptionPane.showMessageDialog(null, "Summary saved to Word document!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No file selected!");
                }
            }
        });
        contentPane.add(btnSummarize);
    }

    private void saveSummaryToWordDoc(String summary) {
        try (XWPFDocument document = new XWPFDocument()) {
			// Split the summary into individual points (assuming each point is separated by a period or newline)
			String[] points = summary.split("\\r?\\n|\\. "); // Splits on new lines or periods followed by a space

			// Create a bullet style
			XWPFParagraph bulletParagraph;
			XWPFRun run;

			for (String point : points) {
			    if (!point.trim().isEmpty()) {
			        // Create a new paragraph for each point
			        bulletParagraph = document.createParagraph();
			        bulletParagraph.setIndentationLeft(720); // Indent the bullet points
			        
			        // Create a run for each paragraph and add a bullet
			        run = bulletParagraph.createRun();
			        run.setText("\u2022 " + point.trim()); // "\u2022" is the Unicode bullet character
			        run.setFontSize(12); // Set font size
			        run.setFontFamily("Times New Roman"); // Set font family
			    }
			}

			File tempFile = null;
			try {
			    // Create a temporary file with a .docx extension
			    tempFile = File.createTempFile("Summary", ".docx");
			    try (FileOutputStream out = new FileOutputStream(tempFile)) {
			        document.write(out);
			    }
			    
			    // Open the temporary file
			    if (Desktop.isDesktopSupported()) {
			        Desktop desktop = Desktop.getDesktop();
			        if (desktop.isSupported(Desktop.Action.OPEN)) {
			            desktop.open(tempFile);
			        } else {
			            JOptionPane.showMessageDialog(null, "Cannot open the file with the default application.");
			        }
			    } else {
			        JOptionPane.showMessageDialog(null, "Desktop API is not supported on this system.");
			    }
			    
			} catch (IOException e) {
			    e.printStackTrace();
			    JOptionPane.showMessageDialog(null, "Error saving the summary to Word document.");
			} finally {
			    // Clean up the temporary file if needed
			    if (tempFile != null && tempFile.exists()) {
			        tempFile.deleteOnExit();
			    }
			}
		} catch (HeadlessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
