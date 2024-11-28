package idi135;

public class PlayerScore {
    public String name;
    public int score;

    // Default constructor is required for Firebase
    public PlayerScore() {}

    // Constructor with parameters
    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
