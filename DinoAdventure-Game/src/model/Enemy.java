package model;

import java.io.*;
import javafx.beans.property.*;

public class Enemy extends Entity implements Living {

    protected EnemyState state;
    protected EnemyState type;
    protected int tps = 1;

    protected IntegerProperty healthProperty = new SimpleIntegerProperty();

    public Enemy() { }

    public Enemy(double x, double y) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
    }

    public Enemy(double x, double y, EnemyState type) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
        this.type = type;
    }

    public int getHealth() {
        return healthProperty.get();
    }

    public void setHealth(int health) {
        healthProperty.set(health);
    }

    public IntegerProperty healthProperty() {
        return healthProperty;
    }

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    public EnemyState getType() {
        return type;
    }

    public void setType(EnemyState type) {
        this.type = type;
    }

    @Override
    public void tick() {

        switch(type) {

            case WANDERING:
                if(tps / Game.FPS == 1) {
                    // Apply specific WanderingEnemy physics/behavior
                    if(Game.random.nextInt(10) == 0) {
                        // Randomly every ~X seconds, toggle whether or not the enemy is walking
                        state = (state == EnemyState.STANDING ? EnemyState.WANDERING : EnemyState.STANDING);
                    }

                    if(Game.random.nextInt(15) == 0) {
                        // Randomly every ~X seconds, toggle which direction the enemy is facing
                        direction = (direction == EntityDirection.LEFT ? EntityDirection.RIGHT : EntityDirection.LEFT);
                    }
                }

                if(state == EnemyState.WANDERING) {
                    if(direction == EntityDirection.LEFT) {
                        xVelocity = Math.max(-maxSpeed, xVelocity - (8 / Game.FPS));
                    } else {
                        xVelocity = Math.min(maxSpeed, xVelocity + (8 / Game.FPS));
                    }
                }
                break;

            case FOLLOWING:
                if(centerPoint.distanceFrom(Game.instance().getPlayer().centerPoint()) <= 1000) {
                    state = EnemyState.FOLLOWING;
                    if(centerPoint.getX() - 15 > Game.instance().getPlayer().centerPoint().getX()) {
                        xVelocity = Math.max(-maxSpeed, xVelocity - (10 / Game.FPS));
                        direction = EntityDirection.LEFT;
                    } else if(centerPoint.getX() + 15 < Game.instance().getPlayer().centerPoint().getX()) {
                        xVelocity = Math.min(maxSpeed, xVelocity + (10 / Game.FPS));
                        direction = EntityDirection.RIGHT;
                    }
                } else {
                    state = EnemyState.STANDING;
                }
                break;

            case FLEEING:
                if(centerPoint.distanceFrom(Game.instance().getPlayer().centerPoint()) <= 500) {
                    state = EnemyState.FOLLOWING;
                    if(centerPoint.getX() > Game.instance().getPlayer().centerPoint().getX()) {
                        xVelocity = Math.min(maxSpeed, xVelocity + (10 / Game.FPS));
                        direction = EntityDirection.RIGHT;
                    } else {
                        xVelocity = Math.max(-maxSpeed, xVelocity - (10 / Game.FPS));
                        direction = EntityDirection.LEFT;
                    }
                } else {
                    state = EnemyState.STANDING;
                }
                break;

            default:
                break;

        }

        // Apply generic entity physics updates
        super.tick();

        tps++;
        if(tps > Game.FPS) {
            tps = 1;
        }

    }

    /**
     * gets the type of the enemy and returns a string value of it's type
     * @return String value of enemy type 
     */
    public String getTypeString() {
        return type.toString();
    }

    // writes the each property to the DataOutputStream passed in the parameters of the enemy to the file to be saved.
    public void serialize(DataOutputStream writer) throws IOException{
            writer.writeDouble(centerPoint().getX());
            writer.writeDouble(centerPoint().getY());
            writer.writeInt(state.ordinal());
            if (super.direction != null){
            writer.writeInt(super.direction.ordinal());
            }
            else{
                writer.writeInt(direction.ordinal());
            }
            writer.writeInt(healthProperty.intValue());
        }

    // reads the DataOutputStream passed in the parameters and sets the Game model accordingly.
    public void deserialize(DataInputStream reader) throws IOException{
        centerPoint().setX(reader.readDouble()); 
        centerPoint().setY(reader.readDouble());
        state = EnemyState.values()[reader.readInt()];
        direction = EntityDirection.values()[reader.readInt()];
        healthProperty = new SimpleIntegerProperty(reader.readInt());
        
    }

    public void setType(String type) {
        this.type = EnemyState.valueOf(type);
    }
    
}
