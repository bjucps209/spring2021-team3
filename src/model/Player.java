package model;

public class Player extends Entity {

    private Game game;
    private Level level;

    public Player(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void tick() {

        // TODO: Implement specific player physics

        // Apply generic entity physics updates
        super.tick();

    }

}
