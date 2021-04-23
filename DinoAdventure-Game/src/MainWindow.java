import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import model.Block;
import model.Collectable;
import model.CollectableType;
import model.DifficultyType;
import model.Enemy;
import model.EnemyState;
import model.EntityDirection;
import model.Game;
import model.GameObserver;
import model.GameState;
import model.Goal;
import model.HighScore;
import model.Level;
import model.Point;
import model.Score;

public class MainWindow implements GameObserver {

    @FXML
    VBox window;
    @FXML
    VBox titlePage;
    @FXML
    VBox highScoresPage;
    @FXML
    VBox newHighscorePage;
    @FXML
    VBox helpPage;
    @FXML
    VBox aboutPage;
    @FXML
    AnchorPane gamePage;
    @FXML
    AnchorPane levelPane;
    @FXML
    ImageView playerImage;
    @FXML
    Pane background;
    @FXML
    Image backgroundImage;

    // GUI controls for Title Screen
    @FXML
    Label title; // title on the title screen
    @FXML
    Label aboutTitle; // title on the about screen
    @FXML
    Label instructions; // title on the instructions screen
    @FXML
    Label mainMenu; // main menu
    @FXML
    TextField name; // textfield for the player to enter their name
    @FXML
    ChoiceBox<String> difficultyLevels; // dropdown for difficulty levels

    @FXML
    ChoiceBox<String> levelsChoice; // dropdown for level selection TODO-update this list to show the levels in
                                    // src/levels/

    @FXML
    ChoiceBox<String> gameMode;

    // GUI controls for Highscores screen
    @FXML
    VBox ranks; // Ranks of the player in the list of highscores
    @FXML
    VBox names; // Names of the player
    @FXML
    VBox scores; // Scores of the player
    @FXML
    VBox levels; // difficulty levels of the game

    // GUI controls for New High score screen
    @FXML
    Label newScore; // new highscore of the player
    @FXML
    Label playerName; // name of the player

    private Timeline gameLoop;

    private boolean keysBound;
    private boolean upKeyPressed;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean escapeKeyPressed;

    private ArrayList<ImageView> enemyImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> collectableImages = new ArrayList<ImageView>();
    private ArrayList<ImageView> goalImages = new ArrayList<ImageView>();

    private int currentLevelIndex = 0;

    HighScore highScores = HighScore.getInstance(); // High scores instantiation

    final MediaPlayer HOME_PAGE_MUSIC = new MediaPlayer(
            new Media(getClass().getResource("assets/sounds/titleScreenMusic.wav").toString()));
    final MediaPlayer ENEMY_ATTACK = new MediaPlayer(
            new Media(getClass().getResource("assets/sounds/EnemyHitPlayer.wav").toString()));
    final MediaPlayer ENEMY_KILLED = new MediaPlayer(
            new Media(getClass().getResource("assets/sounds/PlayerKillsEnemy.wav").toString()));

    @FXML
    public void initialize() throws IOException {

        // Title screen initialization
        title.setTextFill(Color.DARKBLUE);
        // create a font
        Font font = Font.font("Garamond", FontWeight.EXTRA_BOLD, 35);
        // set font of title and main menu
        title.setFont(font);
        mainMenu.setFont(font);

        HOME_PAGE_MUSIC.play();

        // Disable the level selection dropdown unless custom mode is selected (event
        // handler onCustomClicked).
        levelsChoice.setDisable(true);

        // About screen initialization
        aboutTitle.setTextFill(Color.DARKBLUE);
        // set font of title and main menu
        aboutTitle.setFont(font);

        // Help screen initialization
        instructions.setTextFill(Color.WHITE);
        // set font of title and main menu
        instructions.setFont(font);

        // High Scores screen initialization

        /**
         * 1.Load Scores from file 2.get scores list from getScoresList 3.Loop through
         * scores list 4.Display rank, name and scores as labels from scores list
         */
        highScores.loadScores("HighScoreFiles/SaveScoresData.txt");
        List<Score> slist = highScores.getScoresList(); // list of highscores obtained from the scoresList

        for (int i = 0; i < slist.size(); i++) {

            Label rank = new Label();
            Label name = new Label();
            Label scoreLabel = new Label();
            Label difficultyLabel = new Label();

            rank.setText("" + (i + 1));
            rank.setTextFill(Color.WHITE);
            ranks.getChildren().add(rank);

            name.setText(slist.get(i).getName());
            name.setTextFill(Color.WHITE);
            names.getChildren().add(name);

            scoreLabel.setText(String.valueOf(slist.get(i).getScore()));
            scoreLabel.setTextFill(Color.WHITE);
            scores.getChildren().add(scoreLabel);

            difficultyLabel.setText(String.valueOf(slist.get(i).getDifficultyType()));
            difficultyLabel.setTextFill(Color.WHITE);
            levels.getChildren().add(difficultyLabel);
        }

        // find the levels and add them to level choice
        levelsChoice.setValue("Demo");
        File[] files = new File("src/levels").listFiles();
        for (File file : files) {
            if (file.isFile()) {
                levelsChoice.getItems().add(removeFileExtension(file.getName(), true));
            }
        }

        name.requestFocus();

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
                VBox buttons = new VBox();
                buttons.setAlignment(Pos.CENTER);
                buttons.setSpacing(10);
                gameOverPane.getChildren().add(buttons);
                Button restartButton = new Button();
                restartButton.setText("TRY AGAIN");
                restartButton.getStyleClass().add("menuButton");
                buttons.getChildren().add(restartButton);
                Button menuButton = new Button();
                menuButton.setText("MENU");
                menuButton.getStyleClass().add("menuButton");
                buttons.getChildren().add(menuButton);
                menuButton.setOnAction(ev -> {
                    HOME_PAGE_MUSIC.play();
                    Game.instance().setState(GameState.MENU);
                    gamePage.setVisible(false);
                    titlePage.setVisible(true);
                });
                restartButton.setOnAction(ev -> {
                    play(new ActionEvent());
                });

                // Highscores implementation
                Game.instance().setScore(Game.instance().getPlayer().scoreProperty().get());

                Score score = new Score(Game.instance().getUserName(), Game.instance().getScore(),
                        Game.instance().getDifficulty());
                // System.out.println(score.toString());
                try {
                    HighScore.getInstance().loadScores("HighScoreFiles/SaveScoresData.txt");
                    if (HighScore.getInstance().findIfScoreQualifiesAsHigh(score)) {
                        // System.out.println("It is a high Score");
                        // Show the new Score Screen
                        displayNewHighScore();

                        HighScore.getInstance().processScore(score);
                        updateHighScoresScreen();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

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

                VBox buttonsPaused = new VBox();
                buttonsPaused.setAlignment(Pos.CENTER);
                buttonsPaused.setSpacing(10);
                gamePausedPane.getChildren().add(buttonsPaused);
                HBox buttonsPaused2 = new HBox();
                buttonsPaused2.getStyleClass().add("load-save");
                buttonsPaused2.setAlignment(Pos.CENTER);
                buttonsPaused2.setSpacing(10);
                gamePausedPane.getChildren().add(buttonsPaused2);

                Button resumeButton = new Button();
                resumeButton.getStyleClass().add("menuButton");
                resumeButton.setText("RESUME");
                buttonsPaused.getChildren().add(resumeButton);
                Button restartButtonPaused = new Button();
                restartButtonPaused.getStyleClass().add("menuButton");
                restartButtonPaused.setText("RESTART");
                buttonsPaused.getChildren().add(restartButtonPaused);
                Button menuButtonPaused = new Button();
                menuButtonPaused.getStyleClass().add("menuButton");
                menuButtonPaused.setText("MENU");
                buttonsPaused.getChildren().add(menuButtonPaused);
                Button loadButton = new Button();
                loadButton.getStyleClass().add("halfMenuButton");
                loadButton.setText("LOAD");
                buttonsPaused2.getChildren().add(loadButton);
                Button saveButton = new Button();
                saveButton.getStyleClass().add("halfMenuButton");
                saveButton.setText("SAVE");
                buttonsPaused2.getChildren().add(saveButton);

                HBox playButtonHBox = new HBox();
                playButtonHBox.setAlignment(Pos.CENTER_LEFT);
                playButtonHBox.setSpacing(10);
                AnchorPane.setTopAnchor(playButtonHBox, 10.0);
                AnchorPane.setLeftAnchor(playButtonHBox, 10.0);
                gamePage.getChildren().add(playButtonHBox);

                Button playButton = new Button();
                playButton.getStyleClass().add("material-icons");
                playButton.getStyleClass().add("playPauseButton");
                playButton.setText("\ue037");
                playButton.setOnAction(ev -> resumeButton.fire());
                playButtonHBox.getChildren().add(playButton);

                Label playLabel = new Label();
                playLabel.setText("Press Esc to pause/resume");
                playLabel.getStyleClass().add("playPauseLabel");
                playButtonHBox.getChildren().add(playLabel);

                escapeKeyPressed = false;

                Timeline pauseLoop = new Timeline(new KeyFrame(Duration.millis(1000 / Game.FPS), ev -> {
                    if (escapeKeyPressed) {
                        escapeKeyPressed = false;
                        resumeButton.fire();
                    }
                }));
                pauseLoop.setCycleCount(Timeline.INDEFINITE);
                pauseLoop.play();

                menuButtonPaused.setOnAction(ev -> {
                    HOME_PAGE_MUSIC.play();
                    pauseLoop.stop();
                    Game.instance().setState(GameState.MENU);
                    gamePage.setVisible(false);
                    titlePage.setVisible(true);
                });

                resumeButton.setOnAction(ev -> {
                    Game.instance().getCurrentLevel().idleTimeProperty()
                            .set(Game.instance().getCurrentLevel().idleTimeProperty().get() + System.currentTimeMillis()
                                    - gamePausedAt);
                    pauseLoop.stop();
                    gamePage.getChildren().remove(playButtonHBox);
                    gamePage.getChildren().remove(gamePausedPane);
                    Game.instance().setState(GameState.LEVEL_PLAYING);
                    window.getScene().getRoot().requestFocus();
                    gameLoop.play();

                });

                restartButtonPaused.setOnAction(ev -> {
                    pauseLoop.stop();
                    play(new ActionEvent());
                });

                loadButton.setOnAction(ev -> {
                    Game.instance().getCurrentLevel().getEnemies().clear();
                    try {
                        final Game game = Game.instance();
                        game.load("saveFile.dat");

                    } catch (Exception ex) {
                        var alert = new Alert(AlertType.ERROR,
                                "Sorry, something went wrong with loading the file\n error message: \n" + ex);
                        alert.setHeaderText(null);
                        alert.show();
                    }

                    window.getScene().getRoot().requestFocus();

                    Game.instance().observers().forEach(o -> o.update());
                    pauseLoop.stop();
                    window.getScene().getRoot().requestFocus();
                    gameLoop.play();
                    gamePage.getChildren().remove(playButtonHBox);
                    gamePage.getChildren().remove(gamePausedPane);
                    Game.instance().getCurrentLevel().idleTimeProperty()
                            .set(Game.instance().getCurrentLevel().idleTimeProperty().get() + System.currentTimeMillis()
                                    - gamePausedAt);
                    update();
                    // Game.instance().getCurrentLevel().tick();
                });

                saveButton.setOnAction(ev -> {
                    final Game game = Game.instance();
                    try {
                        game.save("saveFile.dat");
                    } catch (Exception ex) {
                        var alert = new Alert(AlertType.ERROR,
                                "Sorry, something went wrong with saving the file\n error message: \n" + ex);
                        alert.setHeaderText(null);
                        alert.show();
                    }
                });

                break;

            case LEVEL_WON:
                gameLoop.stop();
                VBox levelWonPane = new VBox();
                levelWonPane.setAlignment(Pos.CENTER);
                levelWonPane.getStyleClass().add("levelWonPane");
                AnchorPane.setTopAnchor(levelWonPane, 0.0);
                AnchorPane.setLeftAnchor(levelWonPane, 0.0);
                AnchorPane.setRightAnchor(levelWonPane, 0.0);
                levelWonPane.setPrefHeight(window.getHeight());
                levelWonPane.setSpacing(10);
                gamePage.getChildren().add(levelWonPane);
                Label levelWonHeader = new Label();
                levelWonHeader.setText("LEVEL COMPLETE");
                levelWonHeader.getStyleClass().add("levelWonHeader");
                levelWonPane.getChildren().add(levelWonHeader);
                VBox levelWonButtons = new VBox();
                levelWonButtons.setAlignment(Pos.CENTER);
                levelWonButtons.setSpacing(10);
                levelWonPane.getChildren().add(levelWonButtons);
                Button levelWonNextButton = new Button();
                levelWonNextButton.setText("NEXT LEVEL");
                levelWonNextButton.getStyleClass().add("menuButton");
                levelWonButtons.getChildren().add(levelWonNextButton);
                Button levelWonRestartButton = new Button();
                levelWonRestartButton.setText("PLAY AGAIN");
                levelWonRestartButton.getStyleClass().add("menuButton");
                levelWonButtons.getChildren().add(levelWonRestartButton);
                Button levelWonMenuButton = new Button();
                levelWonMenuButton.setText("MENU");
                levelWonMenuButton.getStyleClass().add("menuButton");
                levelWonButtons.getChildren().add(levelWonMenuButton);
                levelWonMenuButton.setOnAction(ev -> {
                    HOME_PAGE_MUSIC.play();
                    Game.instance().setState(GameState.MENU);
                    gamePage.setVisible(false);
                    titlePage.setVisible(true);
                });
                levelWonRestartButton.setOnAction(ev -> {
                    play(new ActionEvent());
                });
                levelWonNextButton.setOnAction(ev -> {
                    // TODO: Implement next level functionality
                });

                break;

            default:
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
                Game.instance().getPlayer().setYVelocity(
                        Game.instance().getPlayer().getYVelocity() - Game.instance().getPlayer().getMaxJumpHeight() - (Game.instance().getPlayer().getEffects().containsKey(CollectableType.FeatherPowerup) ? 4 : 0));
            }
        }

        if (leftKeyPressed && !rightKeyPressed) {
            Game.instance().getPlayer().setMoving(true);
            Game.instance().getPlayer().setXVelocity(Math.max(-(Game.instance().getPlayer().getMaxSpeed() + (Game.instance().getPlayer().getEffects().containsKey(CollectableType.SpeedPowerup) ? 3 : 0)),
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

        if (Game.instance().getPlayer().getMinY() > Game.instance().getCurrentLevel().getHeight()) {

            // If the player falls off the screen, deduct 10 HP
            Game.instance().getPlayer().setHealth(Math.max(0, Game.instance().getPlayer().getHealth() - 10));

            // Clear effects
            Game.instance().getPlayer().getEffects().clear();

            // If the player isn't out of health, respawn them
            if (Game.instance().getPlayer().getHealth() != 0) {
                Game.instance().getPlayer().centerPoint().copyFrom(Game.instance().getCurrentLevel().getSpawnPoint());
                Game.instance().getPlayer().centerPoint().subtract(0, 50);
                Game.instance().getPlayer().setXVelocity(0);
                Game.instance().getPlayer().setYVelocity(0);
            }

        }

    }

    public void update() {

        ArrayList<Node> toRemove = new ArrayList<Node>();

        // Update Player direction
        if (Game.instance().getPlayer().getDirection() == EntityDirection.LEFT) {
            playerImage.setImage(new Image("assets/images/player/player-standing-left-1.png"));
        } else {
            playerImage.setImage(new Image("assets/images/player/player-standing-right-1.png"));
        }

        // Background parallax
        AnchorPane.setRightAnchor(background, 0.0);

        // Side-scrolling logic
        if (Game.instance().getPlayer().centerPoint().getX() > (gamePage.getWidth() / 2) && Game.instance().getPlayer()
                .centerPoint().getX() < Game.instance().getCurrentLevel().getWidth() - (gamePage.getWidth() / 2)) {
            // Scrolling
            AnchorPane.setLeftAnchor(levelPane,
                    ((gamePage.getWidth() / 2) - Game.instance().getPlayer().centerPoint().getX()));
            AnchorPane.setLeftAnchor(background, -(Game.instance().getPlayer().centerPoint().getX() / 2));
        } else if (Game.instance().getPlayer().centerPoint().getX() <= (gamePage.getWidth() / 2)) {
            // Left
            AnchorPane.setLeftAnchor(levelPane, 0.0);
            AnchorPane.setLeftAnchor(background, -(gamePage.getWidth() / 4));
        } else {
            // Right
            AnchorPane.setLeftAnchor(levelPane,
                    (double) (gamePage.getWidth() - Game.instance().getCurrentLevel().getWidth()));
            AnchorPane.setLeftAnchor(background, -(Math.min(Game.instance().getPlayer().centerPoint().getX(),
                    Game.instance().getCurrentLevel().getWidth() - (gamePage.getWidth() / 2)) / 2));
        }

        // Add enemy images
        if (Game.instance().getCurrentLevel().getEnemies().size() != enemyImages.size()) {
            for (Enemy e : Game.instance().getCurrentLevel().getEnemies()) {
                boolean exists = false;
                for (ImageView i : enemyImages) {
                    if ((Enemy) i.getUserData() == e) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    ImageView enemyImage = new ImageView(new Image(
                            "assets/images/enemies/" + e.getType().toString().toLowerCase() + "-standing-left-1.png"));
                    enemyImage.xProperty().bind(e.minXProperty());
                    enemyImage.yProperty().bind(e.minYProperty());
                    enemyImage.setUserData(e);
                    levelPane.getChildren().add(enemyImage);
                    enemyImages.add(enemyImage);
                }
            }
        }

        // Remove/update enemy images
        for (ImageView e : enemyImages) {

            if (Game.instance().getCurrentLevel().getEnemies().contains((Enemy) e.getUserData())) {
                // Enemy still exists
                if (((Enemy) e.getUserData()).getDirection() == EntityDirection.LEFT) {
                    e.setImage(new Image("assets/images/enemies/"
                            + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-standing-left-1.png"));
                } else {
                    e.setImage(new Image("assets/images/enemies/"
                            + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-standing-right-1.png"));
                }
            } else {
                // Enemy no longer exists
                e.setImage(new Image("assets/images/enemies/"
                        + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-dying-right-14.png"));
                toRemove.add(e);
                new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    levelPane.getChildren().remove(e);
                })).play();
            }

            // Remove the enemy if it's falling off the screen
            if (((Enemy) e.getUserData()).getMinY() > window.getHeight()) {

                Game.instance().getCurrentLevel().getEnemies().remove((Enemy) e.getUserData());

                toRemove.add(e);
                levelPane.getChildren().remove(e);

            }
        }

        // Add collectable images
        if (Game.instance().getCurrentLevel().getCollectables().size() != collectableImages.size()) {
            for (Collectable c : Game.instance().getCurrentLevel().getCollectables()) {
                boolean exists = false;
                for (ImageView i : collectableImages) {
                    if ((Collectable) i.getUserData() == c) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    ImageView collectableImage = new ImageView(
                            new Image("assets/images/collectables/" + c.getType().toString().toLowerCase() + ".png"));
                    collectableImage.xProperty().bind(c.minXProperty());
                    collectableImage.yProperty().bind(c.minYProperty());
                    collectableImage.setUserData(c);
                    levelPane.getChildren().add(collectableImage);
                    collectableImages.add(collectableImage);
                }
            }
        }

        // Remove collectable images
        for (ImageView e : collectableImages) {

            if (!Game.instance().getCurrentLevel().getCollectables().contains((Collectable) e.getUserData())) {
                // Collectable no longer exists
                toRemove.add(e);
                levelPane.getChildren().remove(e);
            }

            // Remove the collectable if it's falling off the screen
            if (((Collectable) e.getUserData()).getMinY() > window.getHeight()) {

                Game.instance().getCurrentLevel().getCollectables().remove((Collectable) e.getUserData());

                toRemove.add(e);
                levelPane.getChildren().remove(e);

            }
        }

        // Add goal images
        if (Game.instance().getCurrentLevel().getGoals().size() != goalImages.size()) {
            for (Goal g : Game.instance().getCurrentLevel().getGoals()) {
                boolean exists = false;
                for (ImageView i : goalImages) {
                    if ((Goal) i.getUserData() == g) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    ImageView goalImage = new ImageView(new Image("assets/images/world/finish-flag.png"));
                    goalImage.xProperty().bind(g.minXProperty());
                    goalImage.yProperty().bind(g.minYProperty());
                    goalImage.setUserData(g);
                    levelPane.getChildren().add(goalImage);
                    goalImages.add(goalImage);
                }
            }
        }

        for (Node n : toRemove) {
            enemyImages.remove(n);
            collectableImages.remove(n);
            goalImages.remove(n);
        }

    }

    @FXML
    public void play(ActionEvent e) {

        if ((name.getText().equals("")) || (name.getText().equals(null))) {
            var alert = new Alert(AlertType.INFORMATION, "Please enter a name!");
            alert.setHeaderText(null);
            alert.show();
        } else {
            HOME_PAGE_MUSIC.stop();

            Game.instance().setUserName(name.getText());
            Game.instance().setDifficulty(DifficultyType.valueOf(difficultyLevels.getValue()));

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
            enemyImages.clear();
            Game.instance().getPlayer().getEffects().clear();
            collectableImages.clear();
            goalImages.clear();

            gamePage.setMinWidth(window.getWidth());
            gamePage.setMinHeight(window.getHeight());
            gamePage.setMaxWidth(window.getWidth());
            gamePage.setMaxHeight(window.getHeight());

            // Set background

            background = new Pane();
            backgroundImage = new Image("assets/images/world/background.png");
            background.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,
                    BackgroundRepeat.NO_REPEAT, new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 0, false),
                    new BackgroundSize(window.getWidth(), window.getHeight(), false, false, false, false))));
            AnchorPane.setTopAnchor(background, 0.0);
            AnchorPane.setLeftAnchor(background, 0.0);
            AnchorPane.setRightAnchor(background, 0.0);
            AnchorPane.setBottomAnchor(background, 0.0);
            gamePage.getChildren().add(background);

            levelPane = new AnchorPane();
            gamePage.getChildren().add(levelPane);
            AnchorPane.setTopAnchor(levelPane, 0.0);
            AnchorPane.setLeftAnchor(levelPane, 0.0);
            AnchorPane.setBottomAnchor(levelPane, 0.0);

            // Create/load level

            Level level;

            // Generate some testing dummy terrain
            // Please leave here for now so I can test with it
            // Enable dummy terrain if you want to demo the gameplay

            // boolean dummyTerrain = false;
            if (levelsChoice.getValue().equals("Demo")) {

                level = new Level();
                level.setSpawnPoint(new Point(100, 540));
                level.setWidth(4000);
                level.setHeight(1080);

                // Set level as the current level
                Game.instance().startLevel(level);

                for (int i = 0; i < 10; i++) {
                    ImageView blockImage = new ImageView(
                            new Image("assets/images/world/ground-" + (i == 0 ? "1" : (i == 9 ? "3" : "2")) + ".png"));
                    Block block = new Block();
                    block.centerPoint().setXY(100 + (i * 128), 600);
                    block.setWidth(128);
                    block.setHeight(128);
                    level.getBlocks().add(block);
                    blockImage.xProperty().bind(block.minXProperty());
                    blockImage.yProperty().bind(block.minYProperty());
                    levelPane.getChildren().add(blockImage);
                }

                for (int i = 0; i < 3; i++) {
                    ImageView blockImage = new ImageView(new Image(
                            "assets/images/world/ground-" + (i == 0 ? "13" : (i == 2 ? "15" : "14")) + ".png"));
                    Block block = new Block();
                    block.centerPoint().setXY(500 + (i * 128), 418);
                    block.setWidth(128);
                    block.setHeight(93);
                    level.getBlocks().add(block);
                    blockImage.xProperty().bind(block.minXProperty());
                    blockImage.yProperty().bind(block.minYProperty());
                    levelPane.getChildren().add(blockImage);
                }

                for (int i = 11; i < 30; i++) {
                    ImageView blockImage = new ImageView(new Image(
                            "assets/images/world/ground-" + (i == 11 ? "1" : (i == 29 ? "3" : "2")) + ".png"));
                    Block block = new Block();
                    block.centerPoint().setXY(100 + (i * 128), 600);
                    block.setWidth(128);
                    block.setHeight(128);
                    level.getBlocks().add(block);
                    blockImage.xProperty().bind(block.minXProperty());
                    blockImage.yProperty().bind(block.minYProperty());
                    levelPane.getChildren().add(blockImage);
                }

                Game.instance().getCurrentLevel().getEnemies().add(new Enemy(500, 456, EnemyState.WANDERING));
                Game.instance().getCurrentLevel().getEnemies().add(new Enemy(550, 200, EnemyState.WANDERING));
                Game.instance().getCurrentLevel().getEnemies().add(new Enemy(1200, 456, EnemyState.FOLLOWING));
                Game.instance().getCurrentLevel().getEnemies().add(new Enemy(800, 456, EnemyState.FLEEING));
                Game.instance().getCurrentLevel().getEnemies().add(new Enemy(1100, 456, EnemyState.JUMPING));

                Game.instance().getCurrentLevel().getCollectables()
                        .add(new Collectable(1000, 400, CollectableType.Coin));

                Game.instance().getCurrentLevel().getCollectables()
                        .add(new Collectable(1600, 400, CollectableType.FeatherPowerup));
                Game.instance().getCurrentLevel().getCollectables()
                        .add(new Collectable(1800, 400, CollectableType.SpeedPowerup));
                Game.instance().getCurrentLevel().getCollectables()
                        .add(new Collectable(2000, 400, CollectableType.HealthPowerup));
                Game.instance().getCurrentLevel().getCollectables()
                        .add(new Collectable(2200, 400, CollectableType.CoinPowerup));

                Game.instance().getCurrentLevel().getGoals().add(new Goal(3800, 500));

            } else {

                // TODO: Load level here instead of making a dummy level
                level = new Level();

                // Set level as the current level
                Game.instance().startLevel(level);

                try {
                    level.load("src/levels/" + levelsChoice.getSelectionModel().getSelectedItem() + ".dat");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // Generate real terrain
                level.getBlocks().stream().forEach(block -> {
                    ImageView blockImage = new ImageView(new Image(block.getTexture()));
                    blockImage.xProperty().bind(block.minXProperty());
                    blockImage.yProperty().bind(block.minYProperty());
                    levelPane.getChildren().add(blockImage);
                });
                // Generate Collectables from the level
                level.getCollectables().stream().forEach(enemy -> {
                    // TODO: create logic to load in collectables
                });
            }

            levelPane.setMinWidth(level.getWidth());
            levelPane.setMinHeight(level.getHeight());
            levelPane.setMaxWidth(level.getWidth());
            levelPane.setMaxHeight(level.getHeight());

            // Set difficulty
            switch (Game.instance().getDifficulty()) {
            case EASY:
                Game.instance().getPlayer().setHealth(20);
                Game.instance().getCurrentLevel().maxTimeProperty().set(500 * 1000);
                break;
            case MEDIUM:
                Game.instance().getPlayer().setHealth(15);
                Game.instance().getCurrentLevel().maxTimeProperty().set(400 * 1000);
                break;
            case HARD:
                Game.instance().getPlayer().setHealth(10);
                Game.instance().getCurrentLevel().maxTimeProperty().set(300 * 1000);
                break;
            case CHEAT:
                Game.instance().getPlayer().setHealth(-1);
                Game.instance().getCurrentLevel().maxTimeProperty().set(-1);
                break;
            default:
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

            // Show pause button

            Button pauseButton = new Button();
            pauseButton.getStyleClass().add("material-icons");
            pauseButton.getStyleClass().add("playPauseButton");
            pauseButton.setText("\ue034");
            AnchorPane.setTopAnchor(pauseButton, 10.0);
            AnchorPane.setLeftAnchor(pauseButton, 10.0);
            pauseButton.setOnAction(ev -> Game.instance().setState(GameState.LEVEL_PAUSED));
            gamePage.getChildren().add(pauseButton);

            gamePage.setVisible(true);

            // Create player image in world

            playerImage = new ImageView(new Image("assets/images/player/player-standing-right-1.png"));
            playerImage.xProperty().bind(Game.instance().getPlayer().minXProperty());
            playerImage.yProperty().bind(Bindings.createIntegerBinding(() -> {
                return Game.instance().getPlayer().minYProperty().get() + 2;
            }, Game.instance().getPlayer().minYProperty()));
            levelPane.getChildren().add(playerImage);

            Game.instance().getCurrentLevel().spawnPlayer();

            Game.instance().getCurrentLevel().idleTimeProperty().set(0);

            Game.instance().setState(GameState.LEVEL_PLAYING);
        }
    }

    // Event Handlers for Title Screen
    @FXML
    void onCustomClicked(ActionEvent e) {
        if (gameMode.getSelectionModel().getSelectedItem().equals("CUSTOM")) {
            levelsChoice.setDisable(false);
        } else {
            levelsChoice.setDisable(true);
        }
    }

    @FXML
    void onAboutClicked(ActionEvent event) throws IOException {
        titlePage.setVisible(false);
        aboutPage.setVisible(true);
    }

    @FXML
    void onHelpClicked(ActionEvent event) throws IOException {
        titlePage.setVisible(false);
        helpPage.setVisible(true);
    }

    @FXML
    void onLoadClicked(ActionEvent event) throws IOException {
        HOME_PAGE_MUSIC.stop();
        play(event);
        Game.instance().getCurrentLevel().getEnemies().clear();
        try {
            final Game game = Game.instance();
            game.load("saveFile.dat");

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Something went wrong with loading the file");
        }

        window.getScene().getRoot().requestFocus();

        Game.instance().observers().forEach(o -> o.update());
        window.getScene().getRoot().requestFocus();
        gameLoop.play();
        update();
    }

    @FXML
    void onHighScoreClicked(ActionEvent event) throws IOException {

        File fileObj = new File("HighScoreFiles/SaveScoresData.txt");
        if (fileObj.exists()) {
            titlePage.setVisible(false);
            highScoresPage.setVisible(true);
        } else {
            var alert = new Alert(AlertType.INFORMATION, "There are no high scores yet.");
            alert.setHeaderText(null);
            alert.show();
        }

    }

    @FXML
    void onMainMenuClicked(ActionEvent event) throws IOException {
        highScoresPage.setVisible(false);
        newHighscorePage.setVisible(false);
        aboutPage.setVisible(false);
        helpPage.setVisible(false);
        titlePage.setVisible(true);
    }

    // Event handler to show the NewHighscore screen when the user makes a new
    // highscore.
    @FXML
    public void displayNewHighScore() throws IOException {
        // New High score screen initialization
        playerName.setText(Game.instance().getUserName());
        newScore.setText(String.valueOf(Game.instance().getScore()));
        // Show the new Score Screen
        titlePage.setVisible(false);
        newHighscorePage.setVisible(true);
        gamePage.setVisible(false);
    }

    public void updateHighScoresScreen() throws IOException {
        ranks.getChildren().clear();
        names.getChildren().clear();
        scores.getChildren().clear();
        levels.getChildren().clear();

        highScores.loadScores("HighScoreFiles/SaveScoresData.txt");
        List<Score> slist = highScores.getScoresList(); // list of highscores obtained from the scoresList

        for (int i = 0; i < slist.size(); i++) {

            Label rank = new Label();
            Label name = new Label();
            Label scoreLabel = new Label();
            Label difficultyLabel = new Label();

            rank.setText("" + (i + 1));
            rank.setTextFill(Color.WHITE);
            ranks.getChildren().add(rank);

            name.setText(slist.get(i).getName());
            name.setTextFill(Color.WHITE);
            names.getChildren().add(name);

            scoreLabel.setText(String.valueOf(slist.get(i).getScore()));
            scoreLabel.setTextFill(Color.WHITE);
            scores.getChildren().add(scoreLabel);

            difficultyLabel.setText(String.valueOf(slist.get(i).getDifficultyType()));
            difficultyLabel.setTextFill(Color.WHITE);
            levels.getChildren().add(difficultyLabel);
        }

        

    }
    /**
     * https://www.baeldung.com/java-filename-without-extension
     */
    public static String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }
    
        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }
}
