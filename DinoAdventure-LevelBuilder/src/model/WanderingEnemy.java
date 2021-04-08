package model;

public class WanderingEnemy extends Enemy {
    
    public WanderingEnemy() {
        state = EnemyState.WANDERING;
    }

    @Override
    public void tick() {

        // TODO: Apply specific WanderingEnemy physics/behavior

        // Apply generic enemy physics updates
        super.tick();

    }

}
