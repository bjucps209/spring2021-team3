package model;

public class Game {

    private Player player;
    private Level currentLevel;
    private GameState state;
    private static Game instance;

    private Game() {
        player = new Player();
        state = GameState.MENU;
    }

    public static Game getInstance() {
        return instance instanceof Game ? instance : (instance = new Game());
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
