package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LivingEntity extends Entity {

    protected IntegerProperty healthProperty = new SimpleIntegerProperty();

    public int getHealth() {
        return healthProperty.get();
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
    
}