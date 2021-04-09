// Constructor for a score object and a method to compare two scores.
package model;

// Has a constructor to create a score object and a method to compare two scores.
public class Score implements Comparable<Score> {
    private String name; //Name of the Player with high Score
    private int score;  // Highscore
    private DifficultyType difficultyType; //Difficulty Type of the game

    /**
     * Constructor for the Score Class
     * 
     * @param name - Name of the Player with High Score
     * @param score - HighScore
     * @param difficultyType - Difficulty Type of the game
     * @author
     */
    public Score(String name, int score, DifficultyType difficultyType) {
        this.name = name; // Name of the player with highscore
        this.score = score;
        this.difficultyType = difficultyType;
    }

    /**
     * 
     * @return - returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Takes a string(name) as input for the player's name and sets the name to this string.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return - returns the score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Takes an int(score) as input for the player's score and sets the score to this value.
     * 
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 
     * @return- returns the DifficultyType of the game (EASY, MEDIUM, HARD or CUSTOM).
     */
    public DifficultyType getDifficultyType() {
        return difficultyType;
    }

    /**
     * Sets the DifficultyType of the game to EASY, MEDIUM, HARD or CUSTOM as selected by the player.
     * 
     * @param difficultyType
     */
    public void setDifficultyType(DifficultyType difficultyType) {
        this.difficultyType = difficultyType;
    }

    /** 
     * Parses the Score Object to String
     * @return String
     */
    @Override
    public String toString() {
        return "Score [difficultyType=" + difficultyType + ", name=" + name + ", score=" + score + "]";
    }

    // compares two scores to see which score is greater.
    public int compareTo(Score scoreObj) {
        int scoreToCompare = ((Score) scoreObj).getScore(); // making sure the object is of the same type
        if (this.score > scoreToCompare) {
            return 1;
        }
        else if (this.score < scoreToCompare){
            return -1;
        }
        else {
            return 0;
        }
    }

}
