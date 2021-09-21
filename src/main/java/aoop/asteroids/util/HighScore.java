package aoop.asteroids.util;

public class HighScore implements Comparable {

    private final String userName;
    private int score;

    public HighScore(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public String toString() {
        return this.userName + ": " + this.score;
    }

    @Override
    public int compareTo(Object o) {
        HighScore score = (HighScore) o;
        return Integer.compare(score.score, this.score);
    }
}
