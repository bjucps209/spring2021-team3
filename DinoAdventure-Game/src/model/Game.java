//---------------------------------------------------------------
//File:   Game.jave
//Desc:   Contains the main Model class for DinoAdventure
//---------------------------------------------------------------

package model;

import java.io.*;
import java.util.*;

/**
 * Main model class
 */
public class Game {

    // Instance of the player
    private Player player;

    // Holds the current level
    private Level currentLevel;

    // Holds the current state of the game
    private GameState state;

    // Holds the game instance
    private static Game instance = new Game();

    // Observer pattern
    private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();

    // Holds the users name as a string
    private String userName;

    // Id of the player
    private int id;

    // Holds the current score
    private int score;

    // Holds teh current difficulty
    private DifficultyType difficulty = DifficultyType.EASY;

    // Message to show at game over
    private String gameOverMessage;

    // Tells if the game is in cheat mode
    private boolean cheating;

    // Holds the current level index. Used in normal Mode to keep track of level
    // progress
    private int currentLevelIndex = 0;

    // Random Number Generator
    public static Random random = new Random();

    // Frames Per Second - controlls how fast to update the game
    public static final double FPS = 60;

    // Friction of the game
    public static final double FRICTION = 0.75;

    // Real gravity: 386.0886
    // Used to calculate the speed to pull entities down
    public static final double GRAVITY = 30;

    // Walking speed
    public static final double WALKING_SPEED = 55.11811023622;

    // Running speed
    public static final double RUNNING_SPEED = 155.11811023622;

    /**
     * Create a game instance, set it's state to GameState.MENU
     */
    private Game() {
        player = new Player();
        state = GameState.MENU;
    }

    /**
     * @return true if game is in cheating mode
     */
    public boolean isCheating() {
        return cheating;
    }

    public void setCheating(boolean cheating) {
        this.cheating = cheating;
    }

    public String getGameOverMessage() {
        return gameOverMessage;
    }

    public void setGameOverMessage(String gameOverMessage) {
        this.gameOverMessage = gameOverMessage;
    }

    //Singleton implimintation
    public static Game instance() {
        return instance;
    }

    /**
     * Start the level without resetting the current score
     * @param level
     */
    public void startNextLevel(Level level) {
        currentLevel = level;
        level.startTimeProperty().set(System.currentTimeMillis());
    }

    /**
     * start the level
     * @param level
     */
    public void startLevel(Level level) {
        currentLevel = level;
        player.scoreProperty().set(0);
        level.startTimeProperty().set(System.currentTimeMillis());
    }

    /**
     * Observer object
     * @return view object
     */
    public ArrayList<GameObserver> observers() {
        return observers;
    }

    //Get the current state of the game
    public GameState getState() {
        return state;
    }

    //Set the state of the game
    public void setState(GameState state) {
        this.state = state;
    }

    //Get the player
    public Player getPlayer() {
        return player;
    }

    // Gets the Player's name
    public String getUserName() {
        return userName;
    }

    /**
     * Set the user Name
     * @param userName String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
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

    /**
     * Return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Set the current level to level
     * 
     * @param level level
     */
    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    /**
     * Get the current level index
     */
    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    /**
     * Set the currentLevelIndex
     */
    public void setCurrentLevelIndex(int index) {
        this.currentLevelIndex = index;
    }

    // save the current Game state. Saves the player to file passed in the
    // parameters.
    // Should also save others objects of the game needed to load the game back to a
    // previous state.
    public void save(String filename) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename))) {
            writer.writeUTF(userName);
            writer.writeInt(id);
            writer.writeInt(score);
            writer.writeBoolean(cheating);
            writer.writeInt(currentLevelIndex);
            writer.writeInt(difficulty.ordinal());
            player.serialize(writer);
            if (currentLevel == null) {
                Game.instance().setCurrentLevel(new Level());
            }
            currentLevel.serialize(writer);
        } catch (IOException e) {
            throw new IOException("Something went wrong when writing the file in the save method. :(");

        }
    }

    // read through the filename passed in the parameters to load the game model
    // back to previous state.
    public void load(String filename) throws IOException {
        DataInputStream reader = new DataInputStream(new FileInputStream(filename));
        userName = reader.readUTF();
        id = reader.readInt();
        score = reader.readInt();
        cheating = reader.readBoolean();
        currentLevelIndex = reader.readInt();
        state = GameState.LEVEL_PLAYING;
        difficulty = DifficultyType.values()[reader.readInt()];
        player.deserialize(reader);
        currentLevel.deserialize(reader);
    }
}
