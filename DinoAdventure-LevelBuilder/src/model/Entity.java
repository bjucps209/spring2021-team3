package model;

import java.util.ArrayList;

public class Entity extends Box {

    protected double weight;
    protected double xVelocity;
    protected double yVelocity;
    protected double maxSpeed = 5;
    protected EntityDirection direction;
    protected boolean onSurface;
    
    public void tick() {

        ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();

        // Create dummy boxes for checking where our future velocities will place us
        Box xCheck = new Box();
        xCheck.centerPoint().copyFrom(centerPoint);
        xCheck.widthProperty().set(widthProperty.get());
        xCheck.heightProperty().set(heightProperty.get());
        xCheck.centerPoint().add(xVelocity, 0);
        Box yCheck = new Box();
        yCheck.centerPoint().copyFrom(centerPoint);
        yCheck.widthProperty().set(widthProperty.get());
        yCheck.heightProperty().set(heightProperty.get());
        yCheck.centerPoint().add(0, yVelocity);

        onSurface = false;
    }

    public boolean isOnSurface() {
        return onSurface;
    }

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
