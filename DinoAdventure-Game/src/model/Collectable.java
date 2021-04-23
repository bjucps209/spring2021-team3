package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class Collectable extends Entity {

    private CollectableType type;

    public Collectable(double x, double y, CollectableType type) {
        this.type = type;
        centerPoint.setXY(x, y);
        widthProperty.set(25);
        heightProperty.set(25);
    }

    public void setType(CollectableType type) {
        this.type = type;
    }

    public CollectableType getType() {
        return type;
    }

    public void collect() {
        int effectSeconds = 10;
        switch(type) {
            case Coin:
                Game.instance().getPlayer().scoreProperty().set(Game.instance().getPlayer().scoreProperty().get() + 25);
                break;

            case FeatherPowerup:
            case SpeedPowerup:
                Game.instance().getPlayer().getEffects().put(type, effectSeconds);
                break;

            case HealthPowerup:
                int healthChange = 5;
                Game.instance().getPlayer().setHealth(Game.instance().getPlayer().getHealth() + healthChange);
                break;

            case CoinPowerup:
                int scoreChange = 100;
                Game.instance().getPlayer().setScore(Game.instance().getPlayer().getScore() + scoreChange);
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
