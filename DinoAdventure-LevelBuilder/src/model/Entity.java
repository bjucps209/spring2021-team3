//-------------------------------------------------------------
//File:   Entity.java
//Desc:   extends Box, adds physics properties.
//-------------------------------------------------------------
package model;

/**
 * Class that holds phyisical properties of an entity
 */
public class Entity extends Box {

    // holds the weight of the entity for physics properties
    protected double weight;

    // Holds the x-velocity of the entity
    protected double xVelocity;

    // Holds the y-velocity of the entity
    protected double yVelocity;

    // Holds the maximum speed of the entity
    protected double maxSpeed = 5;

    // Enum that holds the direction of the entity
    protected EntityDirection direction;

    // Tells whether the entity is on the ground or not
    protected boolean onSurface;

    /**
     * returns true if entity is on the ground
     * 
     * @return boolean
     */
    public boolean isOnSurface() {
        return onSurface;
    }

    // Getters/Setters

    public void setWeight(double w) {
        weight = w;
    }

    public double getWeight() {
        return weight;
    }

    public void setXVelocity(double x) {
        xVelocity = x;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setYVelocity(double y) {
        yVelocity = y;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setMaxSpeed(double speed) {
        maxSpeed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
    }

    public EntityDirection getDirection() {
        return direction;
    }

}
