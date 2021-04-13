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
    Button btnNewEnemy;

    @FXML
    TextField txtLevelName;
    
    private ArrayList<ImageView> enemyImages = new ArrayList<ImageView>();

    //change to scrollPane to make bigger levels?
    @FXML
    Pane pane;

    public void initialize() {

        Font font = Font.font("Garamond", FontWeight.EXTRA_BOLD, 16);

        btnSave.setFont(font);
        btnLoad.setFont(font);

        btnSave.setOnAction(e -> {
            try {
                onSaveClicked();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        btnLoad.setOnAction(e -> {
            try {
                onLoadClicked();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        btnNewBlock.setOnAction(e -> onNewBlockClicked());
        btnNewEnemy.setOnAction(e -> onNewEnemyClicked());
        
        

    }

    private void onNewEnemyClicked() {
        var type = EnemyState.WANDERING;
        // Enemy enemy = new Enemy(100, 200, type);
        spawnEnemy(100, 200, type);
        // enemy.setWidth(59);
        // enemy.setHeight(50);
        // enemy.setDirection(EntityDirection.LEFT);
        // enemy.setType(type);
        // LevelDesigner.instance().getLevel().addEntity(enemy);
        // ImageView enemyImage = new ImageView(
        //         new Image("assets/images/enemies/" + type.toString().toLowerCase() + "-standing-left-1.png"));
        // enemyImage.xProperty().bindBidirectional(enemy.centerPoint().xProperty());
        // enemyImage.yProperty().bindBidirectional(enemy.centerPoint().yProperty());
        // enemyImage.setUserData(enemy);
        // pane.getChildren().add(enemyImage);
        // // makeDraggable(enemyImage);
        // enemyImages.add(enemyImage);
        
    }

    private void onNewBlockClicked() {
        var block = new Block();
        block.centerPoint().setXY(200, 100);
        
        LevelDesigner.instance().getLevel().addBlock(block);
        ImageView blockImage = new ImageView(new Image("assets/images/world/ground-2.png"));
        block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
        block.centerPoint().yProperty().bind(blockImage.layoutYProperty());
        block.setWidth(128);
        block.setHeight(128);
        pane.getChildren().add(blockImage);
        makeDraggable(blockImage);
    }

    private void onSaveClicked() throws IOException {
        LevelDesigner.instance().getLevel().save(txtLevelName.getText() + ".dat");

    }

    private void onLoadClicked() throws Exception {
        pane.getChildren().clear();
        LevelDesigner.instance().getLevel().load(txtLevelName.getText() + ".dat");

        LevelDesigner.instance().getLevel().getBlocks().stream().forEach(block -> {
            ImageView blockImage = new ImageView(new Image(block.getTexture()));
            blockImage.layoutXProperty().set(block.centerPoint().xProperty().get());
            blockImage.layoutYProperty().set(block.centerPoint().yProperty().get());
            block.centerPoint().xProperty().bind(blockImage.layoutXProperty());
            block.centerPoint().yProperty().bind(blockImage.layoutYProperty());
            makeDraggable(blockImage);
            pane.getChildren().add(blockImage);
        });
        // Generate enemies from the level
        LevelDesigner.instance().getLevel().getEntites().stream().forEach(enemy -> {
            // spawnEnemy(enemy.centerPoint().getX(), enemy.centerPoint().getY(), enemy.getType());
            ImageView enemyImage = new ImageView(
            new Image("assets/images/enemies/" + enemy.getTypeString() + "-standing-left-1.png"));
            enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
            enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());
            enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
            enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());
            enemyImage.setUserData(enemy);
            pane.getChildren().add(enemyImage);
            makeDraggable(enemyImage);
        });
        // Generate Collectables from the level
        LevelDesigner.instance().getLevel().getCollectables().stream().forEach(enemy -> {
            //TODO: create logic to load in collectables
        });
    }

    // From
    // https://stackoverflow.com/questions/17312734/how-to-make-a-draggable-node-in-javafx-2-0/46696687,
    // with modifications by S. Schaub, C. Zuehlke
    private void makeDraggable(ImageView node) {
        final Delta dragDelta = new Delta();
        node.setOnMouseEntered(me -> node.getScene().setCursor(Cursor.HAND) );
        node.setOnMouseExited(me -> node.getScene().setCursor(Cursor.DEFAULT) );
        node.setOnMousePressed(me -> {
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
        });
        node.setOnMouseReleased(me -> {
            node.getScene().setCursor(Cursor.HAND);
        } );

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
                new Image("assets/images/enemies/" + "wandering" + "-standing-left-1.png"));
        enemyImage.layoutXProperty().set(enemy.centerPoint().xProperty().get());
        enemyImage.layoutYProperty().set(enemy.centerPoint().yProperty().get());
        enemy.centerPoint().xProperty().bind(enemyImage.layoutXProperty());
        enemy.centerPoint().yProperty().bind(enemyImage.layoutYProperty());
        enemyImage.setUserData(enemy);
        pane.getChildren().add(enemyImage);
        makeDraggable(enemyImage);
        enemyImages.add(enemyImage);
    }
}
