// GUI controls and event handlers for the list of Highscores screen
import java.io.IOException;
import java.util.List;

import model.HighScore;
import model.Score;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

// Implementation for the list of Highscores screen
public class HighscorePlayers {

    @FXML
    VBox ranks; // Rank of the player in the list of highscores   
    @FXML
    VBox names; // Name of the player
    @FXML
    VBox scores; // Score of the player
    @FXML
    VBox difficultyLevels; // difficulty level of the game

    HighScore highScores = HighScore.getInstance();

    //initialize method for the list of Highscores screen
    @FXML
    void initialize() throws IOException {
        /**
        1.Load Scores from file
        2.get scores list from getScoresList
        3.Looping through scores list
        4.Display rank, name and scores as labels from scores list
         */
        highScores.loadScores("src/main/resources/SaveScoresData.txt");
        List<Score> slist = highScores.getScoresList(); // list of highscores obtained from the scoresList
           
        for (int i = 0; i < slist.size(); ++i) {

            Label rank = new Label();
            Label name = new Label();
            Label scoreLabel = new Label();
            Label difficultyLabel = new Label();

            rank.setText("" + (i + 1));
            ranks.getChildren().add(rank);

            name.setText(slist.get(i).getName());
            names.getChildren().add(name);

            scoreLabel.setText(String.valueOf(slist.get(i).getScore()));
            scores.getChildren().add(scoreLabel);

            difficultyLabel.setText(String.valueOf(slist.get(i).getDifficultyType()));
            difficultyLevels.getChildren().add(difficultyLabel);


        }
        
    }

}
