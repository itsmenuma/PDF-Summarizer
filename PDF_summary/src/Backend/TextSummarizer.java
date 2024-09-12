package Backend;
import java.util.*;
import java.util.stream.Collectors;

public class TextSummarizer {

    // Method to summarize the text based on level
    public static String summarize(String text, String level) {
        // Split text into sentences
        List<String> sentences = Arrays.asList(text.split("\\. "));
        
        // Calculate word frequencies across the entire text
        Map<String, Integer> wordFrequency = getWordFrequency(text);

        // Create a list of sentence rankings along with their original position
        List<SentenceWithRank> rankedSentences = new ArrayList<>();
        
        for (int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            int rank = getSentenceRank(sentence, wordFrequency);
            rankedSentences.add(new SentenceWithRank(sentence, rank, i)); // Add sentence, rank, and original position
        }
        
        // Sort the sentences by their rank but keep track of their original position
        rankedSentences.sort((a, b) -> Integer.compare(b.rank, a.rank)); // Sort by rank descending

        // Determine how many sentences to keep based on the level
        int totalSentences = rankedSentences.size();
        int sentencesToKeep;

        switch (level.toLowerCase()) {
            case "low":
                sentencesToKeep = (int) (totalSentences * 0.7); // Keep 70%
                break;
            case "medium":
                sentencesToKeep = (int) (totalSentences * 0.5); // Keep 50%
                break;
            case "high":
                sentencesToKeep = (int) (totalSentences * 0.3); // Keep only 30%
                break;
            default:
                sentencesToKeep = totalSentences; // Keep all if something goes wrong
                break;
        }

        // If the document is too short, ensure at least one sentence is kept
        if (sentencesToKeep < 1) {
            sentencesToKeep = 1;
        }

        // Select the top N sentences and sort them back to their original order
        List<SentenceWithRank> selectedSentences = rankedSentences.stream()
                .limit(sentencesToKeep)
                .sorted(Comparator.comparingInt(s -> s.position)) // Sort by original position
                .collect(Collectors.toList());

        // Collect the sentences back into a single string
        return selectedSentences.stream()
                .map(s -> s.sentence)
                .collect(Collectors.joining(". ")) + ".";
    }

    // Calculate word frequency in the entire text
    private static Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+"); // Split by non-word characters
        
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        
        return frequencyMap;
    }

    // Calculate the rank (importance) of a sentence based on word frequency
    private static int getSentenceRank(String sentence, Map<String, Integer> wordFrequency) {
        String[] words = sentence.toLowerCase().split("\\W+");
        int rank = 0;
        
        for (String word : words) {
            rank += wordFrequency.getOrDefault(word, 0); // Add up the frequency of each word
        }
        
        return rank;
    }

    // Class to store sentence along with its rank and original position
    static class SentenceWithRank {
        String sentence;
        int rank;
        int position;

        public SentenceWithRank(String sentence, int rank, int position) {
            this.sentence = sentence;
            this.rank = rank;
            this.position = position;
        }
    }
}
