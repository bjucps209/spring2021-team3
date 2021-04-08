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

    public IntegerProperty healthProperty() {
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
    public void serialize(DataOutputStream writer) throws IOException{
        try {
        writer.writeInt(direction == direction.RIGHT ? 1 : 0); //0 for Left 1 for right
        int stateNum;
        if (state == PlayerState.STANDING)
            stateNum = 0;
        else if (state == PlayerState.RUNNING)
            stateNum = 1;
        else if (state == PlayerState.JUMPING)
            stateNum = 2;
        else if (state == PlayerState.DYING)
            stateNum = 3;
        else{
            stateNum = 4;
        }
        writer.writeInt(stateNum);
        writer.writeInt(scoreProperty.intValue());
        writer.writeInt(healthProperty.intValue());
        writer.writeDouble(centerPoint.getX());
        writer.writeDouble(centerPoint.getY());

        } catch (IOException e) {
            throw new IOException("Some thing went wrong in the serialize method for the player class");
        } 
    }

    public void deserialize(DataInput reader) throws IOException{
        try {
        int directionNum = reader.readInt();
        if (directionNum == 1) {
            direction = direction.RIGHT;
        }else{
            direction = direction.LEFT;
        }
        int stateNum = reader.readInt();
        if (stateNum == 0)
            state = PlayerState.STANDING;
        else if (stateNum == 1)
            state = PlayerState.RUNNING;
        else if (stateNum == 2)
            state = PlayerState.JUMPING;
        else if (stateNum == 3)
            state = PlayerState.DYING;
        else{
            state = PlayerState.DEAD;
        }
        scoreProperty = new SimpleIntegerProperty(reader.readInt());
        healthProperty = new SimpleIntegerProperty(reader.readInt());
        centerPoint.setXY(reader.readDouble(), reader.readDouble());    
        
        } catch (IOException e) {
            throw new IOException("Some thing went wrong in the serialize method for the player class");
        } 
    }

}
