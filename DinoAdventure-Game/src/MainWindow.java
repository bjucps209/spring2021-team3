import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.util.Duration;

public class MainWindow implements GameObserver {

    @FXML
    VBox window;
    @FXML
    VBox titlePage;
    @FXML
    VBox highScorePage;
    @FXML
    VBox helpPage;
    @FXML
    VBox aboutPage;
    @FXML
    AnchorPane gamePage;
    @FXML
    ImageView playerImage;

    // GUI controls for Title Screen
    @FXML
    Label title; // title on the title screen
    @FXML
    TextField name; // textfield for the player to enter their name
    @FXML
    ChoiceBox<String> difficultyLevels; // dropdown for difficulty levels

    private Timeline gameLoop;

    private boolean keysBound;
    private boolean upKeyPressed;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean downKeyPressed;
    private boolean escapeKeyPressed;

    @FXML
    public void initialize() {

        // Title screen initialization
        title.setTextFill(Color.AQUAMARINE);
        // create a font
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 35);
        // set font of title
        title.setFont(font);

        Game.instance().observers().add(this);

        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / Game.FPS), e -> {

            switch (Game.instance().getState()) {

            case LEVEL_PLAYING:
                handleInput();
                Game.instance().getCurrentLevel().tick();
                Game.instance().observers().forEach(o -> o.update());
                break;

            case GAME_OVER:
                gameLoop.stop();
                VBox gameOverPane = new VBox();
                gameOverPane.setAlignment(Pos.CENTER);
                gameOverPane.getStyleClass().add("gameOverPane");
                AnchorPane.setTopAnchor(gameOverPane, 0.0);
                AnchorPane.setLeftAnchor(gameOverPane, 0.0);
                AnchorPane.setRightAnchor(gameOverPane, 0.0);
                gameOverPane.setPrefHeight(window.getHeight());
                gameOverPane.setSpacing(10);
                gamePage.getChildren().add(gameOverPane);
                Label gameOverHeader = new Label();
                gameOverHeader.setText("GAME OVER");
                gameOverHeader.getStyleClass().add("gameOverHeader");
                gameOverPane.getChildren().add(gameOverHeader);
                Label gameOverMessage = new Label();
                gameOverMessage.setText(Game.instance().getGameOverMessage());
                gameOverMessage.getStyleClass().add("gameOverMessage");
                gameOverPane.getChildren().add(gameOverMessage);
                HBox buttons = new HBox();
                buttons.setAlignment(Pos.CENTER);
                buttons.setSpacing(10);
                gameOverPane.getChildren().add(buttons);
                Button menuButton = new Button();
                menuButton.setText("Menu");
                buttons.getChildren().add(menuButton);
                Button restartButton = new Button();
                restartButton.setText("Try Again");
                buttons.getChildren().add(restartButton);
                menuButton.setOnAction(ev -> {
                    Game.instance().setState(GameState.MENU);
                    gamePage.setVisible(false);
                    titlePage.setVisible(true);
                });
                restartButton.setOnAction(ev -> {
                    play(new ActionEvent());
                });
                break;

            case LEVEL_PAUSED:
                gameLoop.stop();
                long gamePausedAt = System.currentTimeMillis();
                VBox gamePausedPane = new VBox();
                gamePausedPane.setAlignment(Pos.CENTER);
                gamePausedPane.getStyleClass().add("gamePausedPane");
                AnchorPane.setTopAnchor(gamePausedPane, 0.0);
                AnchorPane.setLeftAnchor(gamePausedPane, 0.0);
                AnchorPane.setRightAnchor(gamePausedPane, 0.0);
                gamePausedPane.setPrefHeight(window.getHeight());
                gamePausedPane.setSpacing(10);
                gamePage.getChildren().add(gamePausedPane);
                Label gamePausedHeader = new Label();
                gamePausedHeader.setText("GAME PAUSED");
                gamePausedHeader.getStyleClass().add("gamePausedHeader");
                gamePausedPane.getChildren().add(gamePausedHeader);
                HBox buttonsPaused = new HBox();
                buttonsPaused.setAlignment(Pos.CENTER);
                buttonsPaused.setSpacing(10);
                gamePausedPane.getChildren().add(buttonsPaused);
                Button menuButtonPaused = new Button();
                menuButtonPaused.setText("Menu");
                buttonsPaused.getChildren().add(menuButtonPaused);
                Button resumeButton = new Button();
                resumeButton.setText("Resume");
                buttonsPaused.getChildren().add(resumeButton);
                menuButtonPaused.setOnAction(ev -> {
                    Game.instance().setState(GameState.MENU);
                    gamePage.setVisible(false);
                    titlePage.setVisible(true);
                });
                resumeButton.setOnAction(ev -> {
                    gamePage.getChildren().remove(gamePausedPane);
                    Game.instance().getCurrentLevel().maxTimeProperty().add(System.currentTimeMillis() - gamePausedAt);
                    gameLoop.play();
                    Game.instance().setState(GameState.LEVEL_PLAYING);
                });
                break;
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);

    }

    public void handleInput() {

        Game.instance().getPlayer().setMoving(false);

        if (upKeyPressed) {
            Game.instance().getPlayer().setMoving(true);
            if (Game.instance().getPlayer().isOnSurface()) {
                Game.instance().getPlayer().setYVelocity(Game.instance().getPlayer().getYVelocity() - 8);
            }
        }

        if (leftKeyPressed && !rightKeyPressed) {
            Game.instance().getPlayer().setMoving(true);
            Game.instance().getPlayer().setXVelocity(Math.max(-Game.instance().getPlayer().getMaxSpeed(),
                    Game.instance().getPlayer().getXVelocity() - (10 / Game.FPS)));
            Game.instance().getPlayer().setDirection(EntityDirection.LEFT);
        }

        if (rightKeyPressed && !leftKeyPressed) {
            Game.instance().getPlayer().setMoving(true);
            Game.instance().getPlayer().setXVelocity(Math.min(Game.instance().getPlayer().getMaxSpeed(),
                    Game.instance().getPlayer().getXVelocity() + (10 / Game.FPS)));
            Game.instance().getPlayer().setDirection(EntityDirection.RIGHT);
        }

        if (escapeKeyPressed) {
            Game.instance().setState(GameState.LEVEL_PAUSED);
        }

        if (Game.instance().getPlayer().getMaxY() > window.getHeight()) {
            Game.instance().getPlayer().centerPoint().copyFrom(Game.instance().getCurrentLevel().getSpawnPoint());
            Game.instance().getPlayer().setXVelocity(0);
            Game.instance().getPlayer().setYVelocity(0);
            // If the player falls off the screen, deduct 10 HP
            Game.instance().getPlayer().setHealth(Game.instance().getPlayer().getHealth() - 10);
        }

    }

    public void update() {
        if (Game.instance().getPlayer().getDirection() == EntityDirection.LEFT) {
            playerImage.setImage(new Image("assets/images/player/player-standing-left-1.png"));
        } else {
            playerImage.setImage(new Image("assets/images/player/player-standing-right-1.png"));
        }
    }

    @FXML
    public void play(ActionEvent e) {

        window.getScene().getRoot().requestFocus();

        gameLoop.play();

        if (!keysBound) {
            keysBound = true;
            window.getScene().setOnKeyPressed(ev -> {
                if (ev.getCode() == KeyCode.UP) {
                    upKeyPressed = true;
                } else if (ev.getCode() == KeyCode.LEFT) {
                    leftKeyPressed = true;
                } else if (ev.getCode() == KeyCode.RIGHT) {
                    rightKeyPressed = true;
                } else if (ev.getCode() == KeyCode.ESCAPE) {
                    escapeKeyPressed = true;
                }
            });
            window.getScene().setOnKeyReleased(ev -> {
                if (ev.getCode() == KeyCode.UP) {
                    upKeyPressed = false;
                } else if (ev.getCode() == KeyCode.LEFT) {
                    leftKeyPressed = false;
                } else if (ev.getCode() == KeyCode.RIGHT) {
                    rightKeyPressed = false;
                } else if (ev.getCode() == KeyCode.ESCAPE) {
                    escapeKeyPressed = false;
                }
            });
        }

        titlePage.setVisible(false);
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
        level.setSpawnPoint(new Point(100, 540));
        Game.instance().startLevel(level);
        switch (Game.instance().getDifficulty()) {
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

        playerImage = new ImageView(new Image("assets/images/player/player-standing-right-1.png"));
        playerImage.xProperty().bind(Game.instance().getPlayer().minXProperty());
        playerImage.yProperty().bind(Game.instance().getPlayer().minYProperty());
        gamePage.getChildren().add(playerImage);

        // Generate some testing dummy terrain
        // Please leave here for now so I can test with it
        // Enable dummy terrain if you want to demo the gameplay
        boolean dummyTerrain = false;
        if (dummyTerrain) {

            for (int i = 0; i < 10; i++) {
                ImageView blockImage = new ImageView(
                        new Image("assets/images/world/ground-" + (i == 0 ? "1" : (i == 9 ? "3" : "2")) + ".png"));
                Block block = new Block();
                block.centerPoint().setXY(100 + (i * 128), 600);
                block.setWidth(128);
                block.setHeight(128);
                Game.instance().getCurrentLevel().getBlocks().add(block);
                blockImage.xProperty().bind(block.minXProperty());
                blockImage.yProperty().bind(block.minYProperty());
                gamePage.getChildren().add(blockImage);
            }

            for (int i = 0; i < 3; i++) {
                ImageView blockImage = new ImageView(
                        new Image("assets/images/world/ground-" + (i == 0 ? "13" : (i == 2 ? "15" : "14")) + ".png"));
                Block block = new Block();
                block.centerPoint().setXY(500 + (i * 128), 418);
                block.setWidth(128);
                block.setHeight(93);
                Game.instance().getCurrentLevel().getBlocks().add(block);
                blockImage.xProperty().bind(block.minXProperty());
                blockImage.yProperty().bind(block.minYProperty());
                gamePage.getChildren().add(blockImage);
            }

        } else {

            // Generate real terrain
            Game.instance().getCurrentLevel().getBlocks().stream().forEach(block -> {
                ImageView blockImage = new ImageView(new Image("assets/images/world/ground-2.png"));
                blockImage.xProperty().bind(block.minXProperty());
                blockImage.yProperty().bind(block.minYProperty());
                gamePage.getChildren().add(blockImage);
            });
        }

        Game.instance().getPlayer().centerPoint().copyFrom(Game.instance().getCurrentLevel().getSpawnPoint());
        Game.instance().getPlayer().setWidth(50);
        Game.instance().getPlayer().setHeight(54);
        // Game.instance().getPlayer().setXVelocity(1);

    }

        // Event Handlers for Title Screen
        @FXML
        void onAboutClicked(ActionEvent event) throws IOException {
    
        }
    
        @FXML
        void onHelpClicked(ActionEvent event) throws IOException {
    
        }
    
        @FXML
        void onLoadClicked(ActionEvent event) throws IOException {
    
        }
    
        @FXML
        void onHighScoreClicked(ActionEvent event) throws IOException {
            
        }

}
