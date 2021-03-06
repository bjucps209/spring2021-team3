//-------------------------------------------------------------
//File:   Player.java
//Desc:   Class that controlls the player
//-------------------------------------------------------------
package model;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javafx.beans.property.*;

/**
 * Class that contains methods for the player updates
 */
public class Player extends Entity implements Living {

    // Dirrection the player is facing
    private EntityDirection direction = EntityDirection.RIGHT;

    // Holds the current state of the player
    private PlayerState state = PlayerState.STANDING;

    // Property that holds the score
    private IntegerProperty scoreProperty = new SimpleIntegerProperty();

    // Property that holds HP
    private IntegerProperty healthProperty = new SimpleIntegerProperty();

    private EnumMap<CollectableType, Integer> effects = new EnumMap<CollectableType, Integer>(CollectableType.class);

    // Boolean property to tell if player is moving
    private boolean moving;

    // Getters/Setters

    public int getHealth() {
        return healthProperty.get();
    }

    public EnumMap<CollectableType, Integer> getEffects() {
        return effects;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getScore() {
        return scoreProperty.get();
    }

    public void setScore(int score) {
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
    // Apply physics update
    public void tick() {

        // Handle controller input
        for (GameObserver observer : Game.instance().observers()) {
            observer.handleInput();
        }

        // Apply generic entity physics updates
        super.tick();

        // If player runs out of HP and the mode is not cheating mode,
        // show the game over screen.
        if (!Game.instance().isCheating() && healthProperty.get() == 0) {
            Game.instance().setGameOverMessage("You ran out of health!");
            Game.instance().setState(GameState.GAME_OVER);
            Game.instance().getPlayer().setState(PlayerState.DEAD);

            // Play lose soundFX
            Game.instance().observers().forEach(o -> {
                o.playSound("lose");
            });
        }

        //If the player runs out of time, show the game over screen
        if (!Game.instance().isCheating() && Game.instance().getCurrentLevel().remainingTimeProperty().get() <= 0) {
            Game.instance().setGameOverMessage("You ran out of time!");
            Game.instance().setState(GameState.GAME_OVER);
            Game.instance().getPlayer().setState(PlayerState.DEAD);
            Game.instance().observers().forEach(o -> {
                o.playSound("lose");
            });
        }

    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    // writes the each property of the player to the file to be saved.
    public void serialize(DataOutputStream writer) throws IOException {
        try {
            writer.writeInt(direction.ordinal());
            writer.writeInt(state.ordinal());
            writer.writeInt(scoreProperty.intValue());
            writer.writeInt(healthProperty.intValue());
            writer.writeDouble(centerPoint.getX());
            writer.writeDouble(centerPoint.getY());
            writer.writeInt(effects.size());
            for (Entry<CollectableType, Integer> entry : effects.entrySet()) {
                CollectableType type = entry.getKey();
                Integer num = entry.getValue();
                writer.writeInt(type.ordinal());
                writer.writeInt(num);
            }
            writer.writeDouble(xVelocity);
            writer.writeDouble(yVelocity);

        } catch (IOException e) {
            throw new IOException("Some thing went wrong in the serialize method for the player class");
        }
    }

    // reads each property that was saved and loads it back
    public void deserialize(DataInputStream reader) throws IOException {
        try {
            direction = EntityDirection.values()[reader.readInt()];
            state = PlayerState.values()[reader.readInt()];
            scoreProperty.setValue(reader.readInt());
            healthProperty.setValue(reader.readInt());
            centerPoint.setXY(reader.readDouble(), reader.readDouble());
            int sizeNum = reader.readInt();
            effects.clear();
            for (int i = 0; i < sizeNum; i++) {
                CollectableType type = CollectableType.values()[reader.readInt()];
                Integer num = reader.readInt();
                effects.put(type, num);
            }
            this.setXVelocity(reader.readDouble());
            this.setYVelocity(reader.readDouble());
        } catch (IOException e) {
            throw new IOException("Some thing went wrong in the serialize method for the player class");
        }
    }

}
