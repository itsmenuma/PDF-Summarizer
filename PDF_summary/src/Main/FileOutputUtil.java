package Main;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputUtil {

    // Method to write the summary to a text file and open it immediately
    public static void writeSummaryToFileAndOpen(String summary, File outputFile) throws IOException {
        // Write summary to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(summary);
        writer.close();  // Close the writer

        // Open the file automatically after writing
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(outputFile);  // This will open the file with the default text editor
        } else {
            System.out.println("Desktop is not supported. File saved but not opened.");
        }

        System.out.println("Summary saved and file opened: " + outputFile.getAbsolutePath());
    }
}
