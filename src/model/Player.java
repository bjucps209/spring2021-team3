package model;

import java.io.*;

public class Player extends Entity {

    private EntityDirection direction = EntityDirection.RIGHT;
    private PlayerState state = PlayerState.STANDING;
    private int score;

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

    // writes the each property of the player to the file to be saved.
    public void serialize(DataOutputStream writer){
        throw new RuntimeException("The serialize method for Player class not implemented"); 
    }

    public void deserialize(DataInput reader){
        throw new RuntimeException("The deserialize method for Player class not implemented");
    }

}
