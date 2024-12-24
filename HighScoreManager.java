import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "high_scores.txt";
    private static final int MAX_SCORES = 10;

    public static void addHighScore(String name, int score) {
        List<ScoreEntry> scores = loadScores();
        scores.add(new ScoreEntry(name, score));
        Collections.sort(scores);
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        saveScores(scores);
    }

    public static List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                scores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            // No high score file found, return empty list
        }
        return scores;
    }

    private static void saveScores(List<ScoreEntry> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (ScoreEntry entry : scores) {
                writer.write(entry.name + ":" + entry.score);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ScoreEntry implements Comparable<ScoreEntry> {
        String name;
        int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreEntry o) {
            return Integer.compare(o.score, this.score); // Descending order
        }
    }
}
