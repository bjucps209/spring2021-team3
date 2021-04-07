import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.util.Duration;

public class MainWindow {

    @FXML VBox window;
    @FXML VBox startPage;
    @FXML VBox highScorePage;
    @FXML VBox helpPage;
    @FXML VBox aboutPage;
    @FXML AnchorPane gamePage;
    
    private Timeline gameLoop;

    public void initialize() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / Game.FPS), e -> {
            if(Game.instance().getState() == GameState.LEVEL_PLAYING) {
                Game.instance().getCurrentLevel().tick();
                Game.instance().observers().forEach(o -> o.update());
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    @FXML
    public void startGame(ActionEvent e) {

        startPage.setVisible(false);
        gamePage.getChildren().removeAll();

        ImageView background = new ImageView(new Image("assets/images/world/background.png"));
        AnchorPane.setTopAnchor(background, 0.0);
        AnchorPane.setLeftAnchor(background, 0.0);
        AnchorPane.setRightAnchor(background, 0.0);
        AnchorPane.setBottomAnchor(background, 0.0);
        background.fitWidthProperty().bind(window.widthProperty());
        background.setPreserveRatio(true);
        gamePage.getChildren().add(background);

        Level level = new Level();
        level.setMaxTime(300);
        Game.instance().startLevel(level);

        Label timerLabel = new Label();
        timerLabel.getStyleClass().add("gameTimer");
        timerLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf(Game.instance().getCurrentLevel().remainingTimeProperty().get() / 1000);
        }, Game.instance().getCurrentLevel().remainingTimeProperty()));
        AnchorPane.setTopAnchor(timerLabel, 10.0);
        AnchorPane.setRightAnchor(timerLabel, 10.0);
        gamePage.getChildren().add(timerLabel);

        Label testLabel = new Label();
        testLabel.setText("Hello");
        AnchorPane.setTopAnchor(testLabel, 10.0);
        AnchorPane.setLeftAnchor(testLabel, 10.0);
        gamePage.getChildren().add(testLabel);


        gamePage.setVisible(true);


    }

}
