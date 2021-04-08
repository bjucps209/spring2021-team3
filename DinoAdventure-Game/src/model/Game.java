package model;

import java.io.*;
import java.util.*;

public class Game {

    private Player player;
    private Level currentLevel;
    private GameState state;
    private static Game instance = new Game();
    private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
    private DifficultyType difficulty = DifficultyType.EASY;

    public static final double FPS = 60;

    // Real gravity: 386.0886
    public static final double GRAVITY = 25;
    public static final double WALKING_SPEED = 55.11811023622;
    public static final double RUNNING_SPEED = 155.11811023622;

    private Game() {
        player = new Player();
        state = GameState.MENU;
    }

    public static Game instance() {
        return instance;
    }

    public void startLevel(Level level) {
        currentLevel = level;
        player.scoreProperty().set(0);

        level.recordStartTime();
        state = GameState.LEVEL_PLAYING;
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

    /**
     * set the game's difficulty
     * 
     * @param difficulty - difficulty to set to
     */
    public void setDifficulty(DifficultyType difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * get the difficutly of the game
     * 
     * @return difficulty
     */
    public DifficultyType getDifficulty() {
        return difficulty;
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
