package model;

public class Game {

    private Player player;
    private Level currentLevel;
    private GameState state;

    public Game() {
        player = new Player(this);
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

}
