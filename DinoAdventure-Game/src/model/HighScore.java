// Methods to check if a score qualifies as a Highscore, add a highscore to a list of highscores, and load and save highscores. 
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This is the main class for the implementation of highscores. Contains menthods to process a score, add a highscore to a list of highscores, and load and save highscores. 
public class HighScore {
    private List<Score> scoresList = new ArrayList<Score>(); // the list of Highscores
    private int maxNumOfHighScoreEntries = 10; // the maximum number of highscore entries

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
        String fileName = "HighScoreFiles/SaveScoresData.txt";
        File fileObj = new File(fileName);
        if (!fileObj.exists()) {
            fileObj.createNewFile();
        }
        else
        this.loadScores(fileName);

        if (findIfScoreQualifiesAsHigh(score)) {
            addHighScore(score);
            sortScoresList();
            saveScores(fileName);
            loadScores(fileName);
        }
    }

     /**
     * Takes a Score as I/P. Adds the incoming Score into the List. Sorts the socresList. 
     * 
     * @param score - Score Object to be added
     * @throws IOException
     */
    public void addHighScore(Score score) throws IOException {
        scoresList.add(score);
    }

    /**
     * Sorts the list of highscores
     * @throws IOException
     */
    public void sortScoresList() {
        Collections.sort(scoresList, Collections.reverseOrder());
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
        // If the List size is < 10 then the Score Qualifies as High    
        if (scoresList.size() < 10) {
            return true;
        }
        else{
            Score leastScore = scoresList.get(scoresList.size() - 1);
            if (score.getScore() > leastScore.getScore()) {
                return true;
            } else {
                return false;
            }
        }
    }

     /**
     * Loads highscores from the specified file and adds them to the highscore list- scoresList
     * @param fileName - File name to load high scores from.
     * @throws IOException
     */
    public void loadScores(String fileName) throws IOException {
        // 1. Create the File Object to identify the file to work with and verify it exists
        File dir = new File("HighScoreFiles/");
        if(!dir.exists()) dir.mkdir();
        File myFileObj = new File(fileName);
        myFileObj.createNewFile();
        // 2. Create the FileReader object from the File object to work with a character Stream
        // 3. Create the BufferedReader object to read through the stream by passing the FileReader object
        BufferedReader buffObj = new BufferedReader(new FileReader(myFileObj));

        // 4. Clear the Scores list
        scoresList.clear();
        // 5. Read through the BufferedReader obbject line by line
        String line = buffObj.readLine();
        while ((line != null) && line != "") {
            // 6. Parse the individual items in the line into fields
            String[] allFields = line.split(",");
            String name = allFields[0];
            int score = Integer.parseInt(allFields[1]);
            DifficultyType diffType = DifficultyType.valueOf(allFields[2]);
            // 7. Populate the High Scores List with the data from each line
            scoresList.add(new Score(name, score, diffType));
            //Score scr = new Score(name,score,diffType);
            //System.out.println(scr.toString());
            line = buffObj.readLine();
        }
        // Close the file
        buffObj.close();
    }

    /** 
     * Loops through the Scores List and saves the highscores to the specified file. 
     * @param fileName - File to Write to
     */
    public void saveScores(String fileName){
        // Create File object
        File outFile = new File(fileName);
        try {
            PrintWriter outStream = new PrintWriter(new BufferedWriter(new FileWriter(outFile)), true);

            // Save only top 10 scores
            int scoresSize = scoresList.size();
            if (scoresSize > 10) {
                scoresSize = 10;
            }

            for (int i = 0; i < scoresSize; ++i) {
                String line = scoresList.get(i).getName() + "," + scoresList.get(i).getScore() + "," + scoresList.get(i).getDifficultyType();
                outStream.println(line);
            }
            outStream.close();
        } catch (IOException e){
            System.out.println("I/O Error");
            System.exit(0);
        }
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