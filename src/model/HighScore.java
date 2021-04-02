// Methods to check if a score qualifies as a Highscore, add a highscore to a list of highscores, and load and save highscores. 
package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This is the main class for the implementation of highscores. Contains menthods to process a score, add a highscore to a list of highscores, and load and save highscores. 
public class HighScore {
    private List<Score> scoresList = new ArrayList<Score>(); // the list of Highscores
    private int maxNumOfHighScoreEntries = 15; // the maximum number of highscore entries

    /* 
    1. Load the Scores from File
    3. IF new Score qualifies for High Score
    2.   Add Score to List
    3.   Save scores to File
    4.   Load Scores to scoresList for display purpose 
    5. End IF
    */

    /**
     * @param score
     * @throws IOException
     */
    public void processScore(Score score) throws IOException {
        // Load scores
    }

     /**
     * Takes a Score as I/P. Loads the High Scores from File into a Data Structure
     * (scoresList). Adds the incoming Score into the List. Sorts the socresList. 
     * 
     * @param score - Score Object to be added
     * @throws IOException
     */
    public void addHighScore(Score score) throws IOException {

    }

    /**
     * Takes a Score as I/P. 
     * If the List size is < 10 then the Score Qualifies else
     * if Incoming Score > than the lowest score in the List (last element) If so
     *   return 'true' 
     * else 
     *   return 'false'.
     * 
     * @param score - checks if the score qualifies to be a Highscore.
     * @return - True or False
     */
    public boolean findIfScoreQualifiesAsHigh(Score score) {
        return false;
    }

     /**
     * Loads highscores from the specified file and adds them to the highscore list- scoresList
     * @param fileName - File name to load high scores from.
     * @throws IOException
     */
    public void loadScores(String fileName) throws IOException {
    }

    /** 
     * Loops through the Scores List and saves the highscores to the specified file. 
     * @param fileName - File to Write to
     */
    public void saveScores(String fileName){
    }

        /**
     * 
     * @return - returns the list of Highscores.
     */
    public List<Score> getScoresList() {
        return scoresList;
    }

    /**
     * Takes the Highscore list as input and sets it as the scoresList.
     * 
     * @param scoresList
     */
    public void setScoresList(List<Score> scoresList) {
        this.scoresList = scoresList;
    }

    /**
     * 
     * @return - returns the maximum number of high score entries (15).
     */
    public int getMaxNumOfHighScoreEntries() {
        return maxNumOfHighScoreEntries;
    }

    /**
     * sets the maximum number of high score entries to 15. 
     * @param maxNumOfHighScoreEntries
     */
    public void setMaxNumOfHighScoreEntries(int maxNumOfHighScoreEntries) {
        this.maxNumOfHighScoreEntries = maxNumOfHighScoreEntries;
    }

    
    // Singleton implementation
    // prevents direct instantiation outside this class
    private HighScore() {

    }

    private static HighScore instance = new HighScore();

    public static HighScore getInstance() {
        return instance;
    }

    public static void reset() {
        instance = new HighScore();
    }
}