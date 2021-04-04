package model;

import java.io.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.*;

public class Game {

    private Player player;
    private Level currentLevel;
    private GameState state;
    private static Game instance = new Game();
    private Timeline gameLoop;
    private ArrayList<GameObserver> observers;

    private Game() {
        player = new Player();
        state = GameState.MENU;
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / 30), e -> {
            if(state == GameState.LEVEL_PLAYING) {
                Game.instance().getCurrentLevel().tick();
                observers.forEach(o -> o.update());
                // TODO: Game.instance().getCurrentLevel().setRuntimeSeconds(runtime);
            }
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    public static Game instance() {
        return instance;
    }

    public ArrayList<GameObserver> observers() {
        return observers;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    //save the current Game state. Saves the player to file passed in the parameters.
    // Should also save others objects of the game needed to load the game back to a previous state. 
    public void save(String filename)throws IOException{
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename))){
            player.serialize(writer);
            // TODO: writer.writeInt(number of enemies);
            // TODO: loop through all the enemies;
        } catch (IOException e) {
            throw new IOException("Something went wrong when writing the file in the save method. :(");

        }
    }

    //read through the filename passed in the parameters to load the game back to previous state.
    public void load(String filename)throws IOException{
        try (DataInputStream reader = new DataInputStream(new FileInputStream(filename))){
            state = GameState.LEVEL_PLAYING;
            player.deserialize(reader);
            //loop through all the enemies

        }catch (IOException e) {
            throw new IOException("Something went wrong when reading the file in the load message. :(");
        }
    }

}
