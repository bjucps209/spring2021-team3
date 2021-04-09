import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
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


        // Set background

        ImageView background = new ImageView(new Image("assets/images/world/background.png"));
        AnchorPane.setTopAnchor(background, 0.0);
        AnchorPane.setLeftAnchor(background, 0.0);
        AnchorPane.setRightAnchor(background, 0.0);
        AnchorPane.setBottomAnchor(background, 0.0);
        background.fitWidthProperty().bind(window.widthProperty());
        background.setPreserveRatio(true);
        gamePage.getChildren().add(background);


        // Create/load level

        Level level = new Level();
        Game.instance().startLevel(level);
        switch(Game.instance().getDifficulty()) {
            case EASY:
                Game.instance().getPlayer().setHealth(20);
                Game.instance().getCurrentLevel().setMaxTime(500);
                break;
            case MEDIUM:
                Game.instance().getPlayer().setHealth(15);
                Game.instance().getCurrentLevel().setMaxTime(400);
                break;
            case HARD:
                Game.instance().getPlayer().setHealth(10);
                Game.instance().getCurrentLevel().setMaxTime(300);
                break;
        }


        // Show player stats

        VBox dataVBox = new VBox();
        AnchorPane.setTopAnchor(dataVBox, 0.0);
        AnchorPane.setRightAnchor(dataVBox, 0.0);
        dataVBox.getStyleClass().add("dataVBox");



        HBox timerHBox = new HBox();
        timerHBox.setAlignment(Pos.BOTTOM_RIGHT);
        dataVBox.getChildren().add(timerHBox);

        Label timerSecondsLabel = new Label();
        timerSecondsLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf(Game.instance().getCurrentLevel().remainingTimeProperty().get() / 1000) + "s ";
        }, Game.instance().getCurrentLevel().remainingTimeProperty()));
        timerHBox.getChildren().add(timerSecondsLabel);

        Label timerIconLabel = new Label();
        timerIconLabel.setText("\ue425");
        timerIconLabel.getStyleClass().add("material-icons");
        timerHBox.getChildren().add(timerIconLabel);



        HBox scoreHBox = new HBox();
        scoreHBox.setAlignment(Pos.BOTTOM_RIGHT);
        dataVBox.getChildren().add(scoreHBox);

        Label scoreLabel = new Label();
        scoreLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf(Game.instance().getPlayer().scoreProperty().get()) + " ";
        }, Game.instance().getPlayer().scoreProperty()));
        scoreHBox.getChildren().add(scoreLabel);

        Label scoreIconLabel = new Label();
        scoreIconLabel.setText("\uf06f");
        scoreIconLabel.getStyleClass().add("material-icons");
        scoreHBox.getChildren().add(scoreIconLabel);



        HBox healthHBox = new HBox();
        healthHBox.setAlignment(Pos.BOTTOM_RIGHT);
        dataVBox.getChildren().add(healthHBox);

        Label healthLabel = new Label();
        healthLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf(Game.instance().getPlayer().healthProperty().get()) + " ";
        }, Game.instance().getPlayer().healthProperty()));
        healthHBox.getChildren().add(healthLabel);

        Label healthIconLabel = new Label();
        healthIconLabel.setText("\ue87e");
        healthIconLabel.getStyleClass().add("material-icons");
        healthHBox.getChildren().add(healthIconLabel);

        gamePage.getChildren().add(dataVBox);



        gamePage.setVisible(true);



        ImageView playerImage = new ImageView(new Image("assets/images/player/player-standing-right-1.png"));
        playerImage.xProperty().bind(Game.instance().getPlayer().minXProperty());
        playerImage.yProperty().bind(Game.instance().getPlayer().minYProperty());
        gamePage.getChildren().add(playerImage);


        Game.instance().getCurrentLevel().getBlocks().stream().forEach(block -> {
            ImageView blockImage = new ImageView(new Image("assets/images/world/ground-2.png"));
            blockImage.xProperty().bind(block.minXProperty());
            blockImage.yProperty().bind(block.minYProperty());
            gamePage.getChildren().add(blockImage);
        });





        Game.instance().getPlayer().centerPoint().setXY(100, 100);
        Game.instance().getPlayer().setWidth(50);
        Game.instance().getPlayer().setHeight(54);
        //Game.instance().getPlayer().setXVelocity(1);

    }

}
