//---------------------------------------------------------------
//File:   MainWindow.java
//Desc:   View class for LevelBuilder.
//Creator: Christopehr Zuehlke
//---------------------------------------------------------------

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.*;

/**
 * View class for LevelBuilder
 */
public class MainWindow {

    @FXML
    // Main vbox for the view
    VBox VboxMain;

    @FXML
    // button to save the level
    Button btnSave;

    @FXML
    // Button to load the currently selected level
    Button btnLoad;

    @FXML
    // Button to create a new block
    Button btnNewBlock;

    @FXML
    // Button to create a new left-edged block
    Button btnNewLeftBlock;

    @FXML
    // Button to create a new middle block
    Button btnNewMiddleBlock;

    @FXML
    // Button to create a new right edged block
    Button btnNewRightBlock;

    @FXML
    // Button to create a full left block
    Button btnNewLeftFullBlock;

    @FXML
    // Button to create a full right block
    Button btnNewRightFullBlock;

    @FXML
    // Button to create a new fleeing enemy
    Button btnNewFleeingEnemy;

    @FXML
    // Button to create a new following enemy
    Button btnNewFollowingEnemy;

    @FXML
    // Button to create a new jumping enemy
    Button btnNewJumpingEnemy;

    @FXML
    // Button to create a new wandering enemy
    Button btnNewWanderingEnemy;

    @FXML
    // Button to create a new goal
    Button btnNewGoal;

    @FXML
    // Button to create a new feather power-up
    Button btnNewFeatherPowerup;

    @FXML
    // Button to create a new speed power-up
    Button btnNewSpeedPowerup;

    @FXML
    // Button to ceate a new coin power-up
    Button btnNewCoinPowerup;

    @FXML
    // Button to create a new health power-up
    Button btnNewHealthPowerup;

    @FXML
    // Butto to create a new coin
    Button btnNewCoin;

    @FXML
    // text field that holds the name of the level
    TextField txtLevelName;

    @FXML
    // Pane that holds the level images
    Pane pane;

    @FXML
    // Label for the name of level
    Label lblLevelName;

    @FXML
    // Button to create a new level with the name from txtLevelName
    Button btnCreate;

    @FXML
    Label lblLvlWidth;

    @FXML
    // holds the width of the level
    TextField txtWidth;

    @FXML

    Label lblLvlHeight;

    @FXML
    // holds the height of the level
    TextField txtHeight;

    @FXML
    // ScrollPane that contains the create buttons
    ScrollPane scrlPaneButtons;

    @FXML
    // ScrollPane that holds Pane
    ScrollPane scrlPaneLvl;

    @FXML
    // Drop-down field for level choicew
    ChoiceBox<String> levelsChoice;

    // initilize the view
    public void initialize() {

        // Create a new font
        Font font = Font.font("Garamond", FontWeight.EXTRA_BOLD, 14);

        // add font to each button
        btnSave.setFont(font);
        btnLoad.setFont(font);
        btnCreate.setFont(font);
        lblLvlWidth.setFont(font);
        lblLvlHeight.setFont(font);
        lblLevelName.setFont(font);

        // get the list of levels in the folder
        updateLevelist();

        // Set the save method to btnSave
        btnSave.setOnAction(e -> {
            try {
                onSaveClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        // Set the load method btnLoad
        btnLoad.setOnAction(e -> {
            try {
                onLoadClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Set buttons actions
        btnNewBlock.setOnAction(e -> onNewBlockClicked());
        btnNewFleeingEnemy.setOnAction(e -> onNewFleeingEnemyClicked());
        btnNewFollowingEnemy.setOnAction(e -> onNewFollowingEnemyClicked());
        btnNewJumpingEnemy.setOnAction(e -> onNewJumpingEnemyClicked());
        btnNewWanderingEnemy.setOnAction(e -> onNewWanderingEnemyClicked());
        btnNewLeftBlock.setOnAction(e -> onNewLeftBlockClicked());
        btnNewMiddleBlock.setOnAction(e -> onNewMiddleBlockClicked());
        btnNewRightBlock.setOnAction(e -> onNewRightBlockClicked());
        btnNewLeftFullBlock.setOnAction(e -> onNewLeftFullBlockClicked());
        btnNewRightFullBlock.setOnAction(e -> onNewRightFullBlockClicked());
        btnCreate.setOnAction(e -> onCreateClicked());
        btnNewGoal.setOnAction(e -> onNewGoalClicked());
        btnNewCoin.setOnAction(e -> onNewCoinClicked());
        btnNewFeatherPowerup.setOnAction(e -> onNewFeatherPowerupClicked());
        btnNewSpeedPowerup.setOnAction(e -> onNewSpeedPowerupClicked());
        btnNewCoinPowerup.setOnAction(e -> onNewCoinPowerupClicked());
        btnNewHealthPowerup.setOnAction(e -> onNewHealthPowerupClicked());

        // Disabel all buttons until a level is loaded, or created
        setDisableForAllItemButtons(true);

        // spawn Dino
        spawnDino(150, 300);

    }

    /**
     * create a new coin and add it to the level
     */
    private void onNewCoinClicked() {
        var type = CollectableType.Coin;
        spawnCollectable(100, 300, type);
    }

    /**
     * create a new feather and add it to the level
     */
    private void onNewFeatherPowerupClicked() {
        var type = CollectableType.FeatherPowerup;
        spawnCollectable(100, 300, type);
    }

    /**
     * create a new speed power-up
     */
    private void onNewSpeedPowerupClicked() {
        var type = CollectableType.SpeedPowerup;
        spawnCollectable(100, 300, type);
    }

    /**
     * create a new coin power-up and add it to the level
     */
    private void onNewCoinPowerupClicked() {
        var type = CollectableType.CoinPowerup;
        spawnCollectable(100, 300, type);
    }

    /**
     * create a new health power-up and add it to the level
     */
    private void onNewHealthPowerupClicked() {
        var type = CollectableType.HealthPowerup;
        spawnCollectable(100, 300, type);
    }

    /**
     * Create a new goal
     */
    private void onNewGoalClicked() {
        spawnFlag(100, 200);

    }

    /**
     * Create a new fleeing Enemy
     */
    private void onNewFleeingEnemyClicked() {
        var type = EnemyState.FLEEING;
        spawnEnemy(100, 200, type);

    }

    /**
     * Create a new following enemy
     */
    private void onNewFollowingEnemyClicked() {
        var type = EnemyState.FOLLOWING;
        spawnEnemy(100, 200, type);

    }

    /**
     * Create a new jumping enemy
     */
    private void onNewJumpingEnemyClicked() {
        var type = EnemyState.JUMPING;
        spawnEnemy(100, 200, type);

    }

    /**
     * Create a new wandering enemy
     */
    private void onNewWanderingEnemyClicked() {
        var type = EnemyState.WANDERING;
        spawnEnemy(100, 200, type);

    }

    /**
     * Methods to create differnt types of blocks
     */
    private void onNewBlockClicked() {
        spawnBlock("assets/images/world/ground-2.png", 128, 128);
    }

    private void onNewLeftBlockClicked() {
        spawnBlock("assets/images/world/ground-13.png", 128, 93);
    }

    private void onNewMiddleBlockClicked() {
        spawnBlock("assets/images/world/ground-14.png", 128, 93);
    }

    private void onNewRightBlockClicked() {
        spawnBlock("assets/images/world/ground-15.png", 128, 93);
    }

    private void onNewLeftFullBlockClicked() {
        spawnBlock("assets/images/world/ground-1.png", 128, 128);
    }

    private void onNewRightFullBlockClicked() {
        spawnBlock("assets/images/world/ground-3.png", 128, 128);
    }

    /**
     * Create a new level with the name from txtLeveName, and dimisions from
     * txtWidth, txtHeight
     */
    private void onCreateClicked() {

        // If no name is given, display a warning
        if (txtLevelName.getText().equals("")) {
            var alert = new Alert(AlertType.INFORMATION, "Please Enter New Level's Name!");
            alert.setHeaderText(null);
            alert.show();

            // Else create a new level from the given parameters
        } else {

            // Set Pane to the width of the level
            pane.setPrefWidth(Integer.parseInt(txtWidth.getText()));

            // Set the level to the new width
            LevelDesigner.instance().getLevel().setWidth((int) pane.getPrefWidth());

            // Set the height of Pane to that of the level
            pane.setPrefHeight(Integer.parseInt(txtHeight.getText()));

            // Set the level to the new height
            LevelDesigner.instance().getLevel().setHeight((int) pane.getPrefHeight());

            // Chance levelsChoice to select the newly created level
            levelsChoice.setValue(txtLevelName.getText());

            // clear pane for a new level
            pane.getChildren().clear();

            // Spawn dino to hold the spawn location for the player
            spawnDino(150, 300);

            // reset the level designer
            LevelDesigner.reset();
            try {
                onSaveClicked();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // update the list of levels in level choice
            updateLevelist();

            // try to load the level
            try {
                onLoadClicked();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /**
     * Save the current level
     * 
     * @throws IOException
     */
    private void onSaveClicked() throws IOException {
        // Set the level to the current levels width before saving
        LevelDesigner.instance().getLevel().setWidth((int) pane.getPrefWidth());

        // Set the level to the current levels height before saving
        LevelDesigner.instance().getLevel().setHeight((int) pane.getPrefHeight());

        // update the spawn locaiton
        LevelDesigner.instance().getLevel().getSpawnPoint().setX(pane.getChildren().get(0).getLayoutX());
        LevelDesigner.instance().getLevel().getSpawnPoint().setY(pane.getChildren().get(0).getLayoutY());

        // set the panes width
        pane.setPrefWidth(Integer.parseInt(txtWidth.getText()));

        // set the panes height
        pane.setPrefHeight(Integer.parseInt(txtHeight.getText()));

        // set the current levels width
        LevelDesigner.instance().getLevel().setWidth((int) pane.getPrefWidth());

        // Set the current levels height
        LevelDesigner.instance().getLevel().setHeight((int) pane.getPrefHeight());

        // Call the current level's save method
        LevelDesigner.instance().getLevel()
                .save("../DinoAdventure-Game/CustomLevels/" + levelsChoice.getValue() + ".dat");
    }

    /**
     * Load the level that is currently selected in levelsChoice
     * 
     * @throws Exception
     */
    private void onLoadClicked() throws Exception {

        // reset pane's childrin
        pane.getChildren().clear();

        // Call the load method
        LevelDesigner.instance().getLevel()
                .load("../DinoAdventure-Game/CustomLevels/" + levelsChoice.getValue() + ".dat");

        // Update width/height txt fields
        txtWidth.setText(String.valueOf(LevelDesigner.instance().getLevel().getWidth()));
        txtHeight.setText(String.valueOf(LevelDesigner.instance().getLevel().getHeight()));

        // Spawn Dino to the loaded levels spawn point
        spawnDino(LevelDesigner.instance().getLevel().getSpawnPoint().getIntX(),
                LevelDesigner.instance().getLevel().getSpawnPoint().getIntY());

        // Update the panes size
        pane.setPrefWidth(Integer.parseInt(txtWidth.getText()));
        pane.setPrefHeight(Integer.parseInt(txtHeight.getText()));

        // Add the levels goals to the view
        LevelDesigner.instance().getLevel().getGoals().stream().forEach(goal -> {
            // Create a new image
            ImageView flagImage = new ImageView(new Image("assets/images/world/finish-flag.png"));

            // Move it to tge correct location
            flagImage.layoutXProperty().set(goal.centerPoint().getIntX());
            flagImage.layoutYProperty().set(goal.centerPoint().getIntY());

            // Set it's size
            goal.setWidth(40);
            goal.setHeight(46);

            // Bind it's coordinate propeties to the view
            goal.centerPoint().xProperty().bind(flagImage.layoutXProperty());
            goal.centerPoint().yProperty().bind(flagImage.layoutYProperty());

            // Make the goal able to be moved
            makeDraggable(flagImage);

            // Make the goal able to be deletable
            makeflagDeletable(flagImage);

            // Add the goal to the nodes user data
            flagImage.setUserData(goal);

            // Add the goal to the pane
            pane.getChildren().add(flagImage);
        });

        // Add the level's blocks to the view
        LevelDesigner.instance().getLevel().getBlocks().stream().forEach(block -> {

            // Create a new block image with the correct texture
            ImageView blockImage = new ImageView(new Image(block.getTexture()));

            // Set it to the correct location
            blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
            blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());

            // Bind it's location
            block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
            block.centerPoint().yProperty().bind(blockImage.layoutYProperty());

            // Set it's user data
            blockImage.setUserData(block);

            // Make it draggable
            makeDraggable(blockImage);

            // Make it deletable
            makeBlockDeletable(blockImage);

            // add it to the view pane
            pane.getChildren().add(blockImage);
        });

        // Generate enemies from the level
        LevelDesigner.instance().getLevel().getEntites().stream().forEach(enemy -> {
            // Create a new enemy Image
            ImageView enemyImage = new ImageView(
                    new Image("assets/images/enemies/" + enemy.getTypeString().toLowerCase() + "-standing-left-1.png"));

            // Set it to the right location
            enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
            enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());

            // Bind it's location
            enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
            enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());

            // Set it's user data
            enemyImage.setUserData(enemy);

            // make it movable
            makeDraggable(enemyImage);

            // Make it deletable
            makeEnemyDeletable(enemyImage);

            // Add it to the view pane
            pane.getChildren().add(enemyImage);

        });

        // Add the cellectables form the level to the view
        LevelDesigner.instance().getLevel().getCollectables().stream().forEach(c -> {

            // Create a new image
            ImageView collectableImage = new ImageView(
                    new Image("assets/images/collectables/" + c.getType().toString().toLowerCase() + ".png"));

            // move it to the right spot
            collectableImage.layoutXProperty().set(c.centerPoint().xProperty().get());
            collectableImage.layoutYProperty().set(c.centerPoint().yProperty().get());

            // Bind it's location
            c.centerPoint().xProperty().bind(collectableImage.layoutXProperty());
            c.centerPoint().yProperty().bind(collectableImage.layoutYProperty());

            // Set it's userdata
            collectableImage.setUserData(c);

            // Add it to the pane
            pane.getChildren().add(collectableImage);

            // make it movable
            makeDraggable(collectableImage);

            // Make it deletable
            makeCollectableDeletable(collectableImage);

        });

        // Enable all buttons
        setDisableForAllItemButtons(false);
    }

    /**
     * Makes the given Block deletable
     * 
     * @param node block Image
     */
    private void makeBlockDeletable(ImageView node) {
        node.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeBlock((Block) node.getUserData());
                pane.getChildren().remove(node);
            }
        });

    }

    /**
     * Makes the given Enemy Deletable
     */
    private void makeEnemyDeletable(ImageView node) {
        node.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeEntity((Enemy) node.getUserData());
                pane.getChildren().remove(node);
            }
        });

    }

    /**
     * Makes the given collectable deletable
     * 
     * @param node
     */
    private void makeCollectableDeletable(ImageView node) {
        node.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeCollectable((Collectable) node.getUserData());
                pane.getChildren().remove(node);
            }
        });
    }

    /**
     * Makes the given flag deletable
     * 
     * @param node
     */
    private void makeflagDeletable(ImageView node) {
        node.setOnMouseClicked(ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeGoal((Goal) node.getUserData());
                pane.getChildren().remove(node);
            }
        });

    }

    /**
     * Create a new spawn Enemy with the given type and location
     * 
     * @param x    int
     * @param y    int
     * @param type EnemyState
     */
    public void spawnEnemy(double x, double y, EnemyState type) {

        // Create a new enemy
        Enemy enemy = new Enemy(x, y, type);

        // Set it's size
        enemy.setWidth(59);
        enemy.setHeight(50);

        // Set it's direction
        enemy.setDirection(EntityDirection.LEFT);

        // add it to the current level
        LevelDesigner.instance().getLevel().addEntity(enemy);

        // Create a new image
        ImageView enemyImage = new ImageView(
                new Image("assets/images/enemies/" + enemy.getTypeString().toLowerCase() + "-standing-left-1.png"));

        // Move it to the given location
        enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
        enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());

        // Bind it's location to the view
        enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
        enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());

        enemyImage.setUserData(enemy);
        pane.getChildren().add(enemyImage);
        makeDraggable(enemyImage);
        makeEnemyDeletable(enemyImage);
    }

    /**
     * Create a new block with the given texture and size
     * 
     * @param texture String
     * @param Width   int
     * @param Height  int
     */
    public void spawnBlock(String texture, int Width, int Height) {

        // Create a new block
        var block = new Block();

        // Set it's location
        block.centerPoint().setXY(300, 300);

        // Set it's texture
        block.setTexture(texture);

        // Add it to the current level
        LevelDesigner.instance().getLevel().addBlock(block);

        // Create a new image
        ImageView blockImage = new ImageView(new Image(texture));

        // Set it's location
        blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
        blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());

        // Bind it's location the view
        block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
        block.centerPoint().yProperty().bind(blockImage.layoutYProperty());

        // Set it's size
        block.setWidth(Width);
        block.setHeight(Height);

        blockImage.setUserData(block);
        pane.getChildren().add(blockImage);
        makeDraggable(blockImage);
        makeBlockDeletable(blockImage);
    }

    /**
     * Spawn a new block with the given texter at the given location, with the given
     * size
     * 
     * @param texture string
     * @param Width   int
     * @param Height  int
     * @param x       int
     * @param y       int
     */
    public void spawnBlockToLocation(String texture, int Width, int Height, int x, int y) {

        // Create a new block
        var block = new Block();

        // Set it's centerPoint to the given location
        block.centerPoint().setXY(x, y);

        // Set it's texture
        block.setTexture(texture);

        // Add the block to the level
        LevelDesigner.instance().getLevel().addBlock(block);

        // Create a new image
        ImageView blockImage = new ImageView(new Image(texture));

        // Move it to the given location
        blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
        blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());

        // Bind it's location to the view
        block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
        block.centerPoint().yProperty().bind(blockImage.layoutYProperty());

        // Set it's size
        block.setWidth(Width);
        block.setHeight(Height);

        blockImage.setUserData(block);

        pane.getChildren().add(blockImage);
        makeDraggable(blockImage);
        makeBlockDeletable(blockImage);
    }

    /**
     * Spawn a dino at the given coordinate
     * 
     * @param x int
     * @param y int
     */
    public void spawnDino(int x, int y) {
        ImageView Dino = new ImageView(new Image("assets/images/player/player-standing-right-1.png"));
        Dino.layoutXProperty().set(x);
        Dino.layoutYProperty().set(y);
        makeDraggable(Dino);
        pane.getChildren().add(Dino);
    }

    /**
     * Spawn a flag at the given coordinates
     * 
     * @param x int
     * @param y int
     */
    public void spawnFlag(int x, int y) {
        // Create image
        ImageView flag = new ImageView(new Image("assets/images/world/finish-flag.png"));

        // Create a mew gola
        var goal = new Goal(x, y);

        // Move it to the given location
        flag.layoutXProperty().set(goal.centerPoint().xProperty().get());
        flag.layoutYProperty().set(goal.centerPoint().yProperty().get());

        // Bind it's location to the view
        goal.centerPoint().xProperty().bind(flag.layoutXProperty());
        goal.centerPoint().yProperty().bind(flag.layoutYProperty());

        // Set it's size
        goal.setWidth(40);
        goal.setHeight(46);

        makeDraggable(flag);
        makeflagDeletable(flag);

        flag.setUserData(goal);

        // Add it to the pane
        pane.getChildren().add(flag);

        // Add it to the currect level
        LevelDesigner.instance().getLevel().getGoals().add(goal);
    }

    /**
     * Spawn a new collectable at x, y of type type
     * 
     * @param x    int
     * @param y    int
     * @param type CellectableType
     */
    private void spawnCollectable(int x, int y, CollectableType type) {
        // Create a collectable image
        ImageView collectableImage = new ImageView(
                new Image("assets/images/collectables/" + type.name().toLowerCase() + ".png"));

        // Create a new COllectable
        var c = new Collectable(x, y, type);

        // Move it to the correct location
        collectableImage.layoutXProperty().set(c.centerPoint().xProperty().get());
        collectableImage.layoutYProperty().set(c.centerPoint().yProperty().get());

        // Bind it's location to the view
        c.centerPoint().xProperty().bind(collectableImage.layoutXProperty());
        c.centerPoint().yProperty().bind(collectableImage.layoutYProperty());

        // Set it's user data so it can be found
        collectableImage.setUserData(c);

        // add it to the pane
        pane.getChildren().add(collectableImage);

        makeDraggable(collectableImage);
        makeCollectableDeletable(collectableImage);

        // Add it to the level
        LevelDesigner.instance().getLevel().getCollectables().add(c);
    }

    /**
     * Update the list of levels in levelsChoice
     */
    public void updateLevelist() {
        File[] files = new File("../DinoAdventure-Game/CustomLevels/").listFiles();
        String currentLevel = levelsChoice.getValue();
        levelsChoice.getItems().clear();
        for (File file : files) {
            if (file.isFile()) {
                levelsChoice.getItems().add(removeFileExtension(file.getName(), true));

            }
        }
        levelsChoice.setValue(currentLevel);
    }

    /**
     * disable/enable all item buttons
     * 
     * @param t boolean
     */
    public void setDisableForAllItemButtons(boolean t) {
        btnNewBlock.setDisable(t);
        btnNewFleeingEnemy.setDisable(t);
        btnNewFollowingEnemy.setDisable(t);
        btnNewJumpingEnemy.setDisable(t);
        btnNewWanderingEnemy.setDisable(t);
        btnNewLeftBlock.setDisable(t);
        btnNewMiddleBlock.setDisable(t);
        btnNewRightBlock.setDisable(t);
        btnNewLeftFullBlock.setDisable(t);
        btnNewRightFullBlock.setDisable(t);
        btnNewGoal.setDisable(t);
        btnNewCoin.setDisable(t);
        btnNewFeatherPowerup.setDisable(t);
        btnNewSpeedPowerup.setDisable(t);
        btnNewCoinPowerup.setDisable(t);
        btnNewHealthPowerup.setDisable(t);
    }

    // From
    // https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub, C. Zuehlke
    // makes the given node movable
    private void makeDraggable(ImageView node) {
        final Delta dragDelta = new Delta();
        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND));
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT));
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(round(node.getLayoutX() + me.getX() - dragDelta.x, 32));
            node.setLayoutY(round(node.getLayoutY() + me.getY() - dragDelta.y, 32));
        });
        node.setOnMouseReleased(me -> {
            node.getScene().setCursor(Cursor.HAND);
        });

        // Prevent mouse clicks on img from propagating to the pane and
        // resulting in creation of a new image
        node.setOnMouseClicked(me -> me.consume());
    }

    // From
    // https://stackoverflow.com/questions/18407634/rounding-up-to-the-nearest-hundred,
    // with modifications by C. Zuehlke
    /**
     * Round the given number to the closest multiple
     * 
     * @param number  double value to be rounded
     * @param mutiple int value to round to
     */
    int round(double number, int multiple) {

        int result = multiple;

        if (number % multiple == 0) {
            return (int) number;
        }

        // If not already multiple of given number

        if (number % multiple != 0) {

            int division = (int) ((number / multiple) + 1);

            result = division * multiple;

        }
        return result;

    }

    /**
     * Remove's the files extentions
     * https://www.baeldung.com/java-filename-without-extension
     */
    public static String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    /**
     * class that holds x,y location for dragging images around
     */
    private class Delta {
        public double x;
        public double y;
    }
}
