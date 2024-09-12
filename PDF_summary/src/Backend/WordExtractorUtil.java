package Backend;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class WordExtractorUtil {

    public static String extractTextFromDOC(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // Handle .doc files
            if (file.getName().endsWith(".doc")) {
                HWPFDocument doc = new HWPFDocument(fis);
                WordExtractor wordExtractor = new WordExtractor(doc);
                return wordExtractor.getText();
            }
            // Handle .docx files
            else if (file.getName().endsWith(".docx")) {
                XWPFDocument docx = new XWPFDocument(fis);
                XWPFWordExtractor wordExtractor = new XWPFWordExtractor(docx);
                return wordExtractor.getText();
            } else {
                throw new IllegalArgumentException("Unsupported file format.");
            }
        }
    }
}
