package Backend;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocumentFormatter {

    public static void saveFormattedSummary(String summary) {
        try (XWPFDocument document = new XWPFDocument()) {
			// Creating Title/Heading 1
			XWPFParagraph title = document.createParagraph();
			title.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun titleRun = title.createRun();
			titleRun.setText("Document Summary");
			titleRun.setBold(true);
			titleRun.setFontSize(20);
			titleRun.addBreak();

			// Subheading (Heading 2)
			XWPFParagraph subheading = document.createParagraph();
			subheading.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun subheadingRun = subheading.createRun();
			subheadingRun.setText("Summary Points:");
			subheadingRun.setBold(true);
			subheadingRun.setFontSize(16);
			subheadingRun.addBreak();

			// Split the summary into bullet points
			String[] summaryPoints = summary.split("\\. ");
			for (String point : summaryPoints) {
			    if (!point.trim().isEmpty()) {
			        // Create bullet points for each sentence/summary point
			        XWPFParagraph bulletPoint = document.createParagraph();
			        bulletPoint.setAlignment(ParagraphAlignment.LEFT);
			        bulletPoint.setIndentationLeft(400);  // Indent bullet points
			        bulletPoint.setBorderBottom(Borders.SINGLE);
			        XWPFRun bulletRun = bulletPoint.createRun();
			        bulletRun.setText("• " + point.trim() + ".");
			        bulletRun.setFontSize(12);
			        bulletRun.addBreak();
			    }
			}

			// Save the document and automatically open it
			try (FileOutputStream out = new FileOutputStream("Formatted_Summary.docx")) {
			    document.write(out);
			    File file = new File("Formatted_Summary.docx");
			    Desktop.getDesktop().open(file); // Automatically open the Word file
			} catch (IOException e) {
			    e.printStackTrace();
			    JOptionPane.showMessageDialog(null, "Error saving the formatted summary to Word document.");
			}
		} catch (HeadlessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
