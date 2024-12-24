public class GameState {
    private static int score = 0;

    public static int getScore() {
        return score;
    }

    public static void addScore(int points) {
        score += points;
    }

    public static void subtractScore(int points) {
        score -= points;
    }
    public static void resetScore() {
    	score=0;
    }
}
