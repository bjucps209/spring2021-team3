package model;

public class WanderingEnemy extends Enemy {

    private boolean walking;
    
    public WanderingEnemy() {
        state = EnemyState.WANDERING;
    }

    @Override
    public void tick() {

        // Apply specific WanderingEnemy physics/behavior

        if(Game.random.nextInt((int) Game.FPS * 4) == 0) {
            // Randomly every ~X seconds, toggle whether or not the enemy is walking
            walking = !walking;
        }

        if(Game.random.nextInt((int) Game.FPS * 8) == 0) {
            // Randomly every ~X seconds, toggle which direction the enemy is facing
            direction = (direction == EntityDirection.LEFT ? EntityDirection.RIGHT : EntityDirection.LEFT);
        }

        if(walking) {
            if(direction == EntityDirection.LEFT) {
                setXVelocity(Math.max(-getMaxSpeed(), getXVelocity() - (5 / Game.FPS)));
            } else {
                setXVelocity(Math.min(getMaxSpeed(), getXVelocity() + (5 / Game.FPS)));
            }
        }

        // Apply generic enemy physics updates
        super.tick();

    }

}
