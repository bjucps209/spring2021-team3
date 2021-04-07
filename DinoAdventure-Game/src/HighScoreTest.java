// Unit tests for addHighScore loadScores and saveScores methods in Highscore.java

import model.DifficultyType;
import model.Score;
import model.HighScore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

// Contains unit tests for addHighScore loadScores and saveScores methods in Highscore.java
public class HighScoreTest {

    /**
     * Unit Test to verify the adding of a High Score
     * @throws IOException
     */
    @Test
    public void testHighScoresAdd() throws IOException {
        HighScore highScores = UnitTestUtil.setupHighScores();

        // Delete the old SaveScoresData.txt file
        File fileObj = new File("HighScoreFiles/SaveScoresData.txt");
        if (fileObj.exists()) {
            fileObj.delete();
        }

        // Create a Score and add to High Scores
        Score score = new Score("Frank", 20000, DifficultyType.HARD);
        highScores.addHighScore(score);

        assertTrue(score.getName().equals("Frank"));
        assertTrue(score.getScore() == 20000);
        assertEquals(1, highScores.getScoresList().size());
        assertEquals("Frank", highScores.getScoresList().get(0).getName());

        // Create 2nd Score and add to High Scores
        score = new Score("Joe", 25000, DifficultyType.MEDIUM);
        highScores.addHighScore(score);
        assertEquals("Joe", highScores.getScoresList().get(0).getName());

        assertEquals(2, highScores.getScoresList().size());

        // Once Joe is added as above into the High Scores list
        // The Scores would have to be sorted in the allScoresList and
        // also in the file.
        assertEquals("Joe", highScores.getScoresList().get(0).getName());
        assertEquals("Frank", highScores.getScoresList().get(1).getName());

    }

    /**
     * Unit Test for checking sucessful loading of High Scores from File
     * 
     * @throws IOException
     */
    @Test
    public void testLoadScores() throws IOException {
        HighScore highScores = UnitTestUtil.setupHighScores();

        // Load High Scores from File
        String fileName = "HighScoreFiles/HighScoresTestData.txt";
        highScores.loadScores(fileName);

        assertTrue(highScores.getScoresList().get(0).getName().equals("Frank"));
        assertTrue(highScores.getScoresList().get(0).getScore() == 40000);

        assertTrue(highScores.getScoresList().get(1).getName().equals("Joe"));
        assertTrue(highScores.getScoresList().get(1).getScore() == 35000);

        assertTrue(highScores.getScoresList().get(2).getName().equals("Sue"));
        assertTrue(highScores.getScoresList().get(2).getScore() == 30000);
        
        assertEquals(12, highScores.getScoresList().size());
    }
    
    @Test
    public void testSaveScores() {
        HighScore highScores = UnitTestUtil.setupHighScores();

        try {
            highScores.loadScores("HighScoreFiles/HighScoresTestData.txt");
        } catch (IOException e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        }
        highScores.saveScores("HighScoreFiles/SaveScoresData.txt");
        // Check if File SaveScoresTestData.txt exists
        File fileObj =  new File("HighScoreFiles/SaveScoresData.txt");
        assertTrue(fileObj.exists());
    }
}
