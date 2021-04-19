import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.*;

;

public class MainWindow {

    @FXML
    VBox VboxMain;

    @FXML
    Button btnSave;

    @FXML
    Button btnLoad;

    @FXML
    Button btnNewBlock;

    @FXML
    Button btnNewLeftBlock;

    @FXML
    Button btnNewMiddleBlock;

    @FXML
    Button btnNewRightBlock;

    @FXML
    Button btnNewLeftFullBlock;

    @FXML
    Button btnNewRightFullBlock;

    @FXML
    Button btnNewFleeingEnemy;

    @FXML
    Button btnNewFollowingEnemy;

    @FXML
    Button btnNewJumpingEnemy;

    @FXML
    Button btnNewWanderingEnemy;

    @FXML
    Button btnNewGoal;

    @FXML
    TextField txtLevelName;

    @FXML
    Pane pane;

    @FXML
    Label lblLevelName;

    @FXML
    Button btnCreate;

    @FXML
    Label lblLvlWidth;

    @FXML
    TextField txtWidth;

    @FXML
    Label lblLvlHeight;

    @FXML
    TextField txtHeight;

    @FXML
    ScrollPane scrlPaneButtons;

    @FXML
    ScrollPane scrlPaneLvl;


    private ArrayList<ImageView> enemyImages = new ArrayList<ImageView>();
    
    // TODO: Make a button to add a (single) player to the level (but all it really does is set the spawn point)
            

    // TODO: add ability to add collectables

    // TODO: ADD real drag n drop ability
     
    public void initialize() {

        Font font = Font.font("Garamond", FontWeight.EXTRA_BOLD, 16);

        btnSave.setFont(font);
        btnLoad.setFont(font);
        btnCreate.setFont(font);
        lblLvlWidth.setFont(font);
        lblLvlHeight.setFont(font);
        lblLevelName.setFont(font);


        //TODO-
        //add Dino to the default spawn points (non deletable)
        //bind the levels spawn point to Dino's location
        //make him dragable
        
    
        


        btnSave.setOnAction(e -> {
            try {
                onSaveClicked();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnLoad.setOnAction(e -> {
            try {
                onLoadClicked();
            } catch (Exception e1) {
              e1.printStackTrace();
            }
        });
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

        spawnDino(150, 300);
        

    }

    private void onCreateClicked() {
        pane.setPrefWidth(Integer.parseInt(txtWidth.getText()));
        LevelDesigner.instance().getLevel().setWidth((int)pane.getPrefWidth());
        pane.setPrefHeight(Integer.parseInt(txtHeight.getText()));
        LevelDesigner.instance().getLevel().setHeight((int)pane.getPrefHeight());
        
    }


    private void onNewGoalClicked() {
        spawnFlag(100, 200);
        
    }


    private void onNewFleeingEnemyClicked() {
        var type = EnemyState.FLEEING;
        spawnEnemy(100, 200, type);

    }

    private void onNewFollowingEnemyClicked() {
        var type = EnemyState.FOLLOWING;
        spawnEnemy(100, 200, type);

    }

    private void onNewJumpingEnemyClicked() {
        var type = EnemyState.JUMPING;
        spawnEnemy(100, 200, type);

    }

    private void onNewWanderingEnemyClicked() {
        var type = EnemyState.WANDERING;
        spawnEnemy(100, 200, type);

    }

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

    private void onSaveClicked() throws IOException {
        LevelDesigner.instance().getLevel().setWidth((int)pane.getPrefWidth());
        LevelDesigner.instance().getLevel().setHeight((int)pane.getPrefHeight());
        LevelDesigner.instance().getLevel().getSpawnPoint().setX(pane.getChildren().get(0).getLayoutX());
        LevelDesigner.instance().getLevel().getSpawnPoint().setY(pane.getChildren().get(0).getLayoutY());
        LevelDesigner.instance().getLevel().save("../DinoAdventure-Game/src/levels/" + txtLevelName.getText() + ".dat");

    }

    private void onLoadClicked() throws Exception {
        pane.getChildren().clear();
        LevelDesigner.instance().getLevel().load("../DinoAdventure-Game/src/levels/" + txtLevelName.getText() + ".dat");
        txtWidth.setText(String.valueOf(LevelDesigner.instance().getLevel().getWidth()));
        txtHeight.setText(String.valueOf(LevelDesigner.instance().getLevel().getHeight()));
        spawnDino(LevelDesigner.instance().getLevel().getSpawnPoint().getIntX(), 
            LevelDesigner.instance().getLevel().getSpawnPoint().getIntY());
        
        pane.setPrefWidth(Integer.parseInt(txtWidth.getText()));
        pane.setPrefHeight(Integer.parseInt(txtHeight.getText()));
        //
        LevelDesigner.instance().getLevel().getGoals().stream().forEach(goal -> {
            // spawnFlag(flag.centerPoint().getIntX(), flag.centerPoint().getIntY());
            ImageView flagImage = new ImageView(new Image("assets/images/world/finish-flag.png"));
            flagImage.layoutXProperty().set(goal.centerPoint().getIntX());
            flagImage.layoutYProperty().set(goal.centerPoint().getIntY());
            goal.setWidth(40);
            goal.setHeight(46);
            goal.centerPoint().xProperty().bind(flagImage.layoutXProperty());
            goal.centerPoint().yProperty().bind(flagImage.layoutYProperty());
            makeDraggable(flagImage);
            makeflagDeletable(flagImage);
            flagImage.setUserData(goal);
            pane.getChildren().add(flagImage);
        });
        LevelDesigner.instance().getLevel().getBlocks().stream().forEach(block -> {
            ImageView blockImage = new ImageView(new Image(block.getTexture()));
            blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
            blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());
            block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
            block.centerPoint().yProperty().bind(blockImage.layoutYProperty());
            blockImage.setUserData(block);
            makeDraggable(blockImage);
            makeBlockDeletable(blockImage);
            pane.getChildren().add(blockImage);
        });
        // Generate enemies from the level
        LevelDesigner.instance().getLevel().getEntites().stream().forEach(enemy -> {
            // spawnEnemy(enemy.centerPoint().xProperty().get(), enemy.centerPoint().yProperty().get(), enemy.getType());
            ImageView enemyImage = new ImageView(
                    new Image("assets/images/enemies/" + enemy.getTypeString() + "-standing-left-1.png"));
            enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
            enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());
            enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
            enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());
            enemyImage.setUserData(enemy);
            pane.getChildren().add(enemyImage);
            makeDraggable(enemyImage);
            makeEnemyDeletable(enemyImage);
        });
        // Generate Collectables from the level
        LevelDesigner.instance().getLevel().getCollectables().stream().forEach(enemy -> {
            // TODO: create logic to load in collectables
        });
    }


    private void makeBlockDeletable(ImageView node) {
        node.setOnMouseClicked( ev ->
        {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeBlock((Block)node.getUserData());
                pane.getChildren().remove(node);
            }
        });
            
    }

    private void makeEnemyDeletable(ImageView node) {
        node.setOnMouseClicked( ev ->
        {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeEntity((Enemy)node.getUserData());
                pane.getChildren().remove(node);
            }
        });
            
    }

    private void makeCollectableDeletable(ImageView node) {
        node.setOnMouseClicked( ev ->
        {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeCollectable((Collectable)node.getUserData());
                pane.getChildren().remove(node);
            }
        });
            
    }

    private void makeflagDeletable(ImageView node) {
        node.setOnMouseClicked( ev ->
        {
            if (ev.getButton() == MouseButton.SECONDARY) {
                LevelDesigner.instance().getLevel().removeGoal((Goal)node.getUserData());
                pane.getChildren().remove(node);
            }
        });
            
    }

    // From
    // https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub, C. Zuehlke
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

    private class Delta {
        public double x;
        public double y;
    }


    public void spawnEnemy(double x, double y, EnemyState type) {
        Enemy enemy = new Enemy(x, y, type);
        enemy.setWidth(59);
        enemy.setHeight(50);
        enemy.setDirection(EntityDirection.LEFT);
        LevelDesigner.instance().getLevel().addEntity(enemy);
        ImageView enemyImage = new ImageView(
                new Image("assets/images/enemies/" + enemy.getTypeString().toLowerCase() + "-standing-left-1.png"));
        enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
        enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());
        enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
        enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());
        enemyImage.setUserData(enemy);
        pane.getChildren().add(enemyImage);
        makeDraggable(enemyImage);
        makeEnemyDeletable(enemyImage);
        enemyImages.add(enemyImage);
    }

    public void spawnBlock(String texture, int Width, int Height) {
        var block = new Block();
        block.centerPoint().setXY(300, 300);
        block.setTexture(texture);
        LevelDesigner.instance().getLevel().addBlock(block);
        ImageView blockImage = new ImageView(new Image(texture));
        blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
        blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());
        block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
        block.centerPoint().yProperty().bind(blockImage.layoutYProperty());
        block.setWidth(Width);
        block.setHeight(Height);
        blockImage.setUserData(block);
        pane.getChildren().add(blockImage);
        makeDraggable(blockImage);
        makeBlockDeletable(blockImage);
    }
    public void spawnBlockToLocation(String texture, int Width, int Height, int x, int y) {
        var block = new Block();
        block.centerPoint().setXY(x, y);
        block.setTexture(texture);
        LevelDesigner.instance().getLevel().addBlock(block);
        ImageView blockImage = new ImageView(new Image(texture));
        blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
        blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());
        block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
        block.centerPoint().yProperty().bind(blockImage.layoutYProperty());
        block.setWidth(Width);
        block.setHeight(Height);
        blockImage.setUserData(block);
        pane.getChildren().add(blockImage);
        makeDraggable(blockImage);
        makeBlockDeletable(blockImage);
    }

    
    public void spawnDino(int x, int y) {
        ImageView Dino = new ImageView(new Image("assets/images/player/player-standing-right-1.png"));
        Dino.layoutXProperty().set(x);
        Dino.layoutYProperty().set(y);
        makeDraggable(Dino);
        pane.getChildren().add(Dino);
    }

    public void spawnFlag(int x, int y) {
        ImageView flag = new ImageView(new Image("assets/images/world/finish-flag.png"));
        flag.layoutXProperty().set(x);
        flag.layoutYProperty().set(y);
        var goal = new Goal(x, y);
        goal.setWidth(40);
        goal.setHeight(46);
        goal.centerPoint().xProperty().bind(flag.layoutXProperty());
        goal.centerPoint().yProperty().bind(flag.layoutYProperty());
        makeDraggable(flag);
        makeflagDeletable(flag);
        flag.setUserData(goal);
        pane.getChildren().add(flag);
        LevelDesigner.instance().getLevel().getGoals().add(goal);
    }
 

    // From
    // https://stackoverflow.com/questions/18407634/rounding-up-to-the-nearest-hundred,
    // with modifications by C. Zuehlke
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
}
