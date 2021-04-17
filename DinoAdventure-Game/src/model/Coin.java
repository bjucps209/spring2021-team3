package model;

public class Coin extends Entity implements Collectable {
    
    @Override
    public void tick() {

        // Apply generic Collectable physics updates
        super.tick();

        if(Game.instance().getPlayer().overlaps(this)) {
            collect();
        }

    }

    public void collect() {
        Game.instance().getPlayer().scoreProperty().set(Game.instance().getPlayer().scoreProperty().get() + 25);
        Game.instance().getCurrentLevel().getCollectables().remove(this);
    }

}
