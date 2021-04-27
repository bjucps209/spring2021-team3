//-------------------------------------------------------------
//File:   Enemy.java
//Desc:   Enemy class that extends Entity, and holds the enemies state and type.
//-------------------------------------------------------------

package model;

import javafx.beans.property.*;

public class Enemy extends Entity {

    // Enum that holds the state of the enemy
    protected EnemyState state;

    // Enum that holds the type of the enemy
    protected EnemyState type;

    // Property that holds health of enemy
    protected IntegerProperty healthProperty = new SimpleIntegerProperty();

    // create a new enemy
    public Enemy() {
    }

    /**
     * Create a new Enemy at the give x, y coordinates
     * 
     * @param x
     * @param y
     */
    public Enemy(double x, double y) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
    }

    /**
     * Create a new enemy of EnemyState: type, at x, y
     * 
     * @param x
     * @param y
     * @param type
     */
    public Enemy(double x, double y, EnemyState type) {
        centerPoint.xProperty().set(x);
        centerPoint.yProperty().set(y);
        this.type = type;
    }

    /**
     * Set the EnemyType to the give string value
     */
    public void setType(String type) {
        this.type = EnemyState.valueOf(type);
    }

    /**
     * gets the type of the enemy and returns a string value of it's type
     * 
     * @return String value of enemy type
     */
    public String getTypeString() {
        if (type == EnemyState.WANDERING) {
            return "WANDERING";
        } else if (type == EnemyState.FOLLOWING) {
            return "FOLLOWING";
        } else if (type == EnemyState.STANDING) {
            return "STANDING";
        } else if (type == EnemyState.JUMPING) {
            return "JUMPING";
        } else if (type == EnemyState.FLEEING) {
            return "FLEEING";
        }
        return "";
    }

    // Getters and Setters

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

}
