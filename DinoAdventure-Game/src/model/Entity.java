package model;

import java.util.ArrayList;

public class Entity extends Box {

    protected double weight;
    protected double xVelocity;
    protected double yVelocity;
    protected double maxSpeed = 5;
    protected double maxJumpHeight = 8;
    protected EntityDirection direction;
    protected boolean onSurface;
    
    public void tick() {

        ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();

        // Apply gravity
        yVelocity += Game.GRAVITY / Game.FPS;

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

        // Check if the entity would be colliding with a surface based on the future velocities
        for(Block b : Game.instance().getCurrentLevel().getBlocks()) {
            if(b.overlaps(xCheck) && b.overlaps(yCheck)) {
                xVelocity = 0;
                yVelocity = 0;
                if(centerPoint.getY() < b.centerPoint().getY()) {
                    // Handle spawning inside a Block
                    // Move the entity up to the surface if Entity.centerPoint is higher than Block.centerPoint
                    centerPoint.setY(b.getMinY() - (heightProperty.get() / 2) - 1);
                } else {
                    // Handle corners/spawning inside the bottom of a Block
                    // Move the Entity down to the bottom of a Block if Block.centerPoint is higher than Entity.centerPoint
                    centerPoint.setY(b.getMaxY() + (heightProperty.get() / 2) + 1);
                }
            } else if(b.overlaps(xCheck)) {
                // Handle hitting a wall
                xVelocity = 0;
            } else if(b.overlaps(yCheck)) {
                // Handle hitting a ceiling/landing on a surface
                yVelocity = 0;
                if(centerPoint.getY() < b.centerPoint().getY()) {
                    centerPoint.setY(b.getMinY() - (heightProperty.get() / 2) - 1);
                    onSurface = true;
                }
            }
        }

        // Handle Player colliding with Enemy
        if(this instanceof Player) {
            for(Enemy e : Game.instance().getCurrentLevel().getEnemies()) {
                if(e.overlaps(xCheck) || e.overlaps(yCheck)) {

                    if(e.overlaps(xCheck)) {
                        if(centerPoint.getX() < e.centerPoint().getX()) {
                            xVelocity -= 5;
                        } else {
                            xVelocity += 5;
                        }
                        yVelocity -= 5;
                    }

                    if(getMaxY() < e.getMinY()) {
                        if(yVelocity > 0) {
                            yVelocity = -15;
                        }
                        e.setHealth(e.getHealth() - 1);
                        if(e.getHealth() <= 0) {
                            Game.instance().observers().forEach(o -> {
                                o.playSound("pke");
                            });
                            int coins = Game.random.nextInt(3) + 1;
                            if(e.getType() == EnemyState.SCHAUB) {
                                coins = 100;
                            }
                            enemiesToRemove.add(e);
                            for(int i = 0; i < coins; i++) {
                                Collectable coin = new Collectable(e.centerPoint().getX(), e.centerPoint().getY(), CollectableType.Coin);
                                coin.xVelocity = (Game.random.nextInt(100) - 50) / 10;
                                coin.yVelocity = -Game.random.nextInt(5);
                                Game.instance().getCurrentLevel().getCollectables().add(coin);
                            }
                            if(Game.random.nextInt(5) == 0) {
                                CollectableType t = CollectableType.values()[Game.random.nextInt(CollectableType.values().length)];
                                Collectable c = new Collectable(e.centerPoint().getX(), e.centerPoint().getY(), t);
                                c.xVelocity = (Game.random.nextInt(100) - 50) / 10;
                                c.yVelocity = -Game.random.nextInt(5);
                                Game.instance().getCurrentLevel().getCollectables().add(c);
                            }
                            if(e.getType() == EnemyState.SCHAUB) {
                                Goal g = new Goal(e.centerPoint().getX(), e.centerPoint().getY());
                                Game.instance().getCurrentLevel().getGoals().add(g);
                            }
                        } else {
                            Game.instance().observers().forEach(o -> {
                                o.playSound("phe");
                            });
                        }
                    } else {
                        Game.instance().observers().forEach(o -> {
                        o.playSound("ehp");
                        });
                        ((Player) this).setHealth(Math.max(0, ((Player) this).getHealth() - 1));
                        ((Player) this).getEffects().clear();
                    }

                }
            }
        }

        if(onSurface && !(this instanceof Player && ((Player) this).isMoving())) {
            // Apply friction unless this Entity is a Player and the user is moving the Player
            xVelocity = xVelocity * Game.FRICTION;
        }

        centerPoint.add(xVelocity, yVelocity);

        for(Enemy e : enemiesToRemove) Game.instance().getCurrentLevel().getEnemies().remove(e);

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

    public void setMaxJumpHeight(double height) {
        maxJumpHeight = height;
    }

    public double getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
    }

    public EntityDirection getDirection() {
        return direction;
    }

}
