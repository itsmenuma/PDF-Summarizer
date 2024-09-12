package Main;
import java.io.File;
import java.io.IOException;

public class SummarizerApp {

    public static void main(String[] args) {
        // Example of what happens when the Summarize button is clicked
        try {
            // Assume you have the extracted summary from the document
            String summary = "This is your document's summary content.";

            // Specify the output file location (can be anywhere)
            File outputFile = new File("summary_output.txt");

            // Write the summary to the file and open it immediately
            FileOutputUtil.writeSummaryToFileAndOpen(summary, outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
