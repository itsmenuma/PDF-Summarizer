package Main;
import java.io.File;
import java.io.IOException;

import Backend.WordExtractorUtil;

public class FileExtractor {

    public static String extractText(File file, String fileType) {
        try {
            if (fileType.equalsIgnoreCase("DOC") || fileType.equalsIgnoreCase("DOCX")) {
                return WordExtractorUtil.extractTextFromDOC(file);
            }
            // Handle other file types like PDF, etc.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
