package model;

import javafx.application.Platform;

public class Collectable extends Entity {

    private CollectableType type;

    public Collectable(double x, double y, CollectableType type) {
        this.type = type;
        centerPoint.setXY(x, y);
        switch(type) {
            case Coin:
                widthProperty.set(25);
                heightProperty.set(25);
                break;
            default:
                // TODO: Set powerup width and height here
                break;
        }
    }

    public void setType(CollectableType type) {
        this.type = type;
    }

    public CollectableType getType() {
        return type;
    }

    public void collect() {
        switch(type) {
            case Coin:
                Game.instance().getPlayer().scoreProperty().set(Game.instance().getPlayer().scoreProperty().get() + 25);
                break;

            default:
                break;
        }
        Platform.runLater(() -> Game.instance().getCurrentLevel().getCollectables().remove(this));
    }

    public void tick() {

        // Apply generic Collectable physics updates
        super.tick();

        if(Game.instance().getPlayer().overlaps(this)) {
            collect();
        }

    }

}
