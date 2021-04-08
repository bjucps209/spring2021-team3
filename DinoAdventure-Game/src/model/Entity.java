package model;

public class Entity extends Box {

    protected double weight;
    protected double xVelocity;
    protected double yVelocity;
    protected double maxSpeed;
    
    public void tick() {

        // Apply gravity
        yVelocity += Game.GRAVITY / Game.FPS;

        // Create a dummy box to check whether our velocity places us on a surface
        boolean hittingSurfaceX = false;
        boolean hittingSurfaceY = false;
        Box surfaceCheck = new Box();
        surfaceCheck.centerPoint().copyFrom(centerPoint);
        surfaceCheck.widthProperty().set(widthProperty.get());
        surfaceCheck.heightProperty().set(heightProperty.get());
        surfaceCheck.centerPoint().add(xVelocity, yVelocity);

        // Check if the entity would be colliding with a surface based on the future velocities
        for(Block b : Game.instance().getCurrentLevel().getBlocks()) {
            if(b.overlaps(surfaceCheck)) {
                yVelocity = -(yVelocity / 2);
            }
        }

        if(hittingSurfaceX) {
            // Stop the entity if it's moving to the side
            xVelocity = 0;
        }

        if(hittingSurfaceY) {
            // Stop the entity if it's moving downward
            yVelocity = 0;
        }

        centerPoint.add(xVelocity, yVelocity);

    }

    public String getType() {
        return null;
    }

    public void setXVelocity(double x) {
        xVelocity = x;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public void setYVelocity(double y) {
        xVelocity = y;
    }

    public double getYVelocity() {
        return yVelocity;
    }

}
