package model;

import java.io.*;
import javafx.beans.property.*;

public class Player extends Entity implements Living {

    private EntityDirection direction = EntityDirection.RIGHT;
    private PlayerState state = PlayerState.STANDING;
    private IntegerProperty scoreProperty = new SimpleIntegerProperty();
    private IntegerProperty healthProperty = new SimpleIntegerProperty();

    public int getHealth() {
        return healthProperty.get();
    }

    public int getScoreProperty() {
        return scoreProperty.get();
    }

    public void setScoreProperty(int score) {
        scoreProperty.set(score);
    }

    public IntegerProperty scoreProperty() {
        return scoreProperty;
    }

    public void setHealth(int health) {
        healthProperty.set(health);
    }

    public IntegerProperty getHealthProperty() {
        return healthProperty;
    }

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
