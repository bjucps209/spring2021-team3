
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Node;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

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
    Label mainMenu; // main menu
    @FXML
    TextField name; // textfield for the player to enter their name
    @FXML
    ChoiceBox<String> difficultyLevels; // dropdown for difficulty levels

    private Timeline gameLoop;

    private boolean keysBound;
    private boolean upKeyPressed;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean escapeKeyPressed;

    private ArrayList<ImageView> enemyImages = new ArrayList<ImageView>();

    @FXML
    public void initialize() {

        // Title screen initialization
        title.setTextFill(Color.DARKBLUE);
        // create a font
        Font font = Font.font("Garamond", FontWeight.EXTRA_BOLD, 35);
        // set font of title and main menu
        title.setFont(font);
        mainMenu.setFont(font);

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
                resumeButton.getStyleClass().add("pausedButton");
                resumeButton.setText("RESUME");
                buttonsPaused.getChildren().add(resumeButton);
                Button restartButtonPaused = new Button();
                restartButtonPaused.getStyleClass().add("pausedButton");
                restartButtonPaused.setText("RESTART");
                buttonsPaused.getChildren().add(restartButtonPaused);
                Button menuButtonPaused = new Button();
                menuButtonPaused.getStyleClass().add("pausedButton");
                menuButtonPaused.setText("MENU");
                buttonsPaused.getChildren().add(menuButtonPaused);
                Button loadButton = new Button();
                loadButton.getStyleClass().add("pausedButton2");
                loadButton.setText("LOAD");
                buttonsPaused2.getChildren().add(loadButton);
                Button saveButton = new Button();
                saveButton.getStyleClass().add("pausedButton2");
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

                //bugs: timer doesn't load the right time and doesn't load enemies properly
                loadButton.setOnAction(ev -> {
                    try{
                    final Game game = Game.instance();
                    game.load("saveFile.dat"); // time is the long at which the System was paused and the save button was clicked
                    
                   
                    } catch (Exception ex){
                        System.out.println("Something went wrong with loading the file");
                    }
                    pauseLoop.stop();
                    gamePage.getChildren().remove(playButtonHBox);
                    gamePage.getChildren().remove(gamePausedPane);

                    // committed out code below causes this to be thrown for me:
                    // a java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "InvokeLaterDispatcher" java.lang.OutOfMemoryError: Java heap space

                    // for ( int i = 0; i < gamePage.getChildren().size(); i++){
                    //     if (gamePage.getChildren().get(i) instanceof Node){
                    //         Node node = (Node) gamePage.getChildren().get(i);
                    //         if ( node.getUserData() instanceof Enemy){
                    //             gamePage.getChildren().remove(node);
                    //         }
                    //     }
                    // }
                    // window.getScene().getRoot().requestFocus();
                    // for (int i = 0; i < Game.instance().getCurrentLevel().getEntites().size(); i++){
                    //     if (Game.instance().getCurrentLevel().getEntites().get(i) instanceof Enemy){
                    //         Enemy enemy = Game.instance().getCurrentLevel().getEntites().get(i);
                    //         spawnEnemy(enemy.getMaxX(), enemy.getMaxY(), enemy.getType());
                    //     }
                    // }

                update();
                gameLoop.play();
                });

                saveButton.setOnAction(ev -> {
                    final Game game = Game.instance();
                    try{
                    game.save("saveFile.dat");
                    } catch (Exception ex){
                        System.out.println(ex);
                        System.out.println("Something went wrong with saving the file");
                    }
                    /**You should just be able to save from and load into the runTimeProperty and the maxTimeProperty. 
                     * You also need to set the startTimeProperty to the current time minus runTimeProperty
                     *  when you load the level. The rest of the values are calculated based on those */
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

        if (Game.instance().getPlayer().getMinY() > window.getHeight()) {

            // If the player falls off the screen, deduct 10 HP
            Game.instance().getPlayer().setHealth(Game.instance().getPlayer().getHealth() - 10);

            // If the player isn't out of health, respawn them
            if (Game.instance().getPlayer().getHealth() > 0) {
                Game.instance().getPlayer().centerPoint().copyFrom(Game.instance().getCurrentLevel().getSpawnPoint());
                Game.instance().getPlayer().centerPoint().subtract(0, 50);
                Game.instance().getPlayer().setXVelocity(0);
                Game.instance().getPlayer().setYVelocity(0);
            }

        }

    }

    public void update() {

        ArrayList<Node> toRemove = new ArrayList<Node>();

        if (Game.instance().getPlayer().getDirection() == EntityDirection.LEFT) {
            playerImage.setImage(new Image("assets/images/player/player-standing-left-1.png"));
        } else {
            playerImage.setImage(new Image("assets/images/player/player-standing-right-1.png"));
        }

        for (ImageView e : enemyImages) {

            if (Game.instance().getCurrentLevel().getEntites().contains((Enemy) e.getUserData())) {
                if (((Enemy) e.getUserData()).getDirection() == EntityDirection.LEFT) {
                    e.setImage(new Image("assets/images/enemies/"
                            + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-standing-left-1.png"));
                } else {
                    e.setImage(new Image("assets/images/enemies/"
                            + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-standing-right-1.png"));
                }
            } else {
                e.setImage(new Image("assets/images/enemies/"
                        + ((Enemy) e.getUserData()).getType().toString().toLowerCase() + "-dying-right-14.png"));
                toRemove.add(e);
                new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    gamePage.getChildren().remove(e);
                })).play();
            }

            // Remove the enemy if it's falling off the screen
            if (((Enemy) e.getUserData()).getMinY() > window.getHeight()) {
    
                Game.instance().getCurrentLevel().getEntites().remove((Enemy) e.getUserData());

                toRemove.add(e);
                gamePage.getChildren().remove(e);

            }
        }

        for (Node n : toRemove)
            enemyImages.remove(n);

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

        Level level;

        // Generate some testing dummy terrain
        // Please leave here for now so I can test with it
        // Enable dummy terrain if you want to demo the gameplay
        boolean dummyTerrain = true;
        if (dummyTerrain) {


            level = new Level();
            level.setSpawnPoint(new Point(100, 540));

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
                gamePage.getChildren().add(blockImage);
            }

            for (int i = 0; i < 3; i++) {
                ImageView blockImage = new ImageView(
                        new Image("assets/images/world/ground-" + (i == 0 ? "13" : (i == 2 ? "15" : "14")) + ".png"));
                Block block = new Block();
                block.centerPoint().setXY(500 + (i * 128), 418);
                block.setWidth(128);
                block.setHeight(93);
                level.getBlocks().add(block);
                blockImage.xProperty().bind(block.minXProperty());
                blockImage.yProperty().bind(block.minYProperty());
                gamePage.getChildren().add(blockImage);
            }

            spawnEnemy(500, 456, EnemyState.WANDERING);
            spawnEnemy(550, 200, EnemyState.WANDERING);
            spawnEnemy(1200, 456, EnemyState.FOLLOWING);
            spawnEnemy(800, 456, EnemyState.FLEEING);
            spawnEnemy(1100, 456, EnemyState.JUMPING);

        } else {


            // TODO: Load level here instead of making a dummy level
            level = new Level();
            level.setSpawnPoint(new Point(100, 540));

        
            try {
                level.load("lvl1.dat");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            // Set level as the current level
            Game.instance().startLevel(level);

            // Generate real terrain
            Game.instance().getCurrentLevel().getBlocks().stream().forEach(block -> {
                ImageView blockImage = new ImageView(new Image(block.getTexture()));
                blockImage.xProperty().bind(block.minXProperty());
                blockImage.yProperty().bind(block.minYProperty());
                gamePage.getChildren().add(blockImage);
            });
            // Generate enemies from the level
            Game.instance().getCurrentLevel().getEntites().stream().forEach(enemy -> {
                ImageView enemyImage = new ImageView(
                new Image("assets/images/enemies/" + enemy.getTypeString() + "-standing-left-1.png"));
                enemyImage.xProperty().bind(enemy.minXProperty());
                enemyImage.yProperty().bind(enemy.minYProperty());
                enemyImage.setUserData(enemy);
                gamePage.getChildren().add(enemyImage);
                enemyImages.add(enemyImage);
                // spawnEnemy(enemy.centerPoint().getX(), enemy.centerPoint().getY(), EnemyState.WANDERING);

            });
            // Generate Collectables from the level
            Game.instance().getCurrentLevel().getCollectables().stream().forEach(enemy -> {
                //TODO: create logic to load in collectables
            });
        }


        // Set difficulty
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
        playerImage.yProperty().bind(Game.instance().getPlayer().minYProperty());
        gamePage.getChildren().add(playerImage);

        spawnPlayer(Game.instance().getCurrentLevel().getSpawnPoint().getX(), Game.instance().getCurrentLevel().getSpawnPoint().getY());

        Game.instance().getCurrentLevel().idleTimeProperty().set(0);

    }

    public void spawnPlayer(double x, double y) {
        Game.instance().getPlayer().setWidth(50);
        Game.instance().getPlayer().setHeight(54);
        Game.instance().getPlayer().centerPoint().setX(x);
        Game.instance().getPlayer().centerPoint().setY(y);
        Game.instance().getPlayer().setDirection(EntityDirection.RIGHT);
    }

    public void spawnEnemy(double x, double y, EnemyState type) {
        Enemy enemy = new Enemy(x, y, type);
        enemy.setWidth(59);
        enemy.setHeight(50);
        enemy.setDirection(EntityDirection.LEFT);
        Game.instance().getCurrentLevel().addEntity(enemy);
        ImageView enemyImage = new ImageView(
                new Image("assets/images/enemies/" + type.toString().toLowerCase() + "-standing-left-1.png"));
        enemyImage.xProperty().bind(enemy.minXProperty());
        enemyImage.yProperty().bind(enemy.minYProperty());
        enemyImage.setUserData(enemy);
        gamePage.getChildren().add(enemyImage);
        enemyImages.add(enemyImage);
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
