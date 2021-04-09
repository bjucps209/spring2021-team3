package model;

public class Entity extends Box {

    protected double weight;
    protected double xVelocity;
    protected double yVelocity;
    protected double maxSpeed = 5;
    protected EntityDirection direction;
    protected boolean onSurface;
    
    public void tick() {

        // Apply gravity
        yVelocity += Game.GRAVITY / Game.FPS;

        // Create a dummy box to check whether our velocity places us on a surface
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

        // Check if the entity would be colliding with a surface based on the future velocities
        for(Block b : Game.instance().getCurrentLevel().getBlocks()) {
            if(b.overlaps(xCheck)) {
                xVelocity = 0;
                // if(centerPoint.getX() < b.centerPoint().getX()) {
                //     centerPoint.setX(b.getMinX() - (widthProperty.get() / 2));
                // }
                // if(centerPoint.getX() > b.centerPoint().getX()) {
                //     centerPoint.setX(b.getMaxX() + (widthProperty.get() / 2));
                // }
            }
            if(b.overlaps(yCheck)) {
                yVelocity = 0;
                if(centerPoint.getY() < b.centerPoint().getY()) {
                    centerPoint.setY(b.getMinY() - (heightProperty.get() / 2) - 1);
                    onSurface = true;
                }
            }
        }

        if(onSurface && !(this instanceof Player && ((Player) this).isMoving())) {
            // Apply friction
            xVelocity = xVelocity * 0.75;
        }

        centerPoint.add(xVelocity, yVelocity);

    }

    public String getType() {
        return null;
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
