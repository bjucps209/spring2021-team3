package model;

public class Player extends Entity {

    private EntityDirection direction = EntityDirection.RIGHT;
    private PlayerState state = PlayerState.STANDING;

    @Override
    public void tick() {

        // TODO: Implement specific player physics

        // Apply generic entity physics updates
        super.tick();

    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

}
