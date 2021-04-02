package model;

public class Enemy extends Entity {

    protected EnemyState state;
    protected EntityDirection direction = EntityDirection.LEFT;

    @Override
    public void tick() {

        // Apply generic entity physics updates
        super.tick();

    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

}
