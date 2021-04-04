package model;

public class Coin extends Entity implements Collectable {
    
    @Override
    public void tick() {

        // Apply generic Collectable physics updates
        super.tick();

    }

    public void collect() {
        // TODO
    }

}
