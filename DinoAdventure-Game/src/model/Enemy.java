package model;

import java.io.*;
import javafx.beans.property.*;

public abstract class Enemy extends Entity implements Living {

    protected EnemyState state;
    protected EntityDirection direction = EntityDirection.LEFT;

    protected IntegerProperty healthProperty = new SimpleIntegerProperty();

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
    
}
