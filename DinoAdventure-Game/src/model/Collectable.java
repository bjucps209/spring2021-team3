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
        switch(type) {
            case Coin:
                Game.instance().getPlayer().scoreProperty().set(Game.instance().getPlayer().scoreProperty().get() + 25);
                break;

            case SpeedPowerup:
                int speedChange = 3;
                Game.instance().getPlayer().setMaxSpeed(Game.instance().getPlayer().getMaxSpeed() + speedChange);
                new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                    Game.instance().getPlayer().setMaxSpeed(Game.instance().getPlayer().getMaxSpeed() - speedChange);
                })).play();
                break;

            case FeatherPowerup:
                int jumpChange = 4;
                Game.instance().getPlayer().setMaxJumpHeight(Game.instance().getPlayer().getMaxJumpHeight() + jumpChange);
                new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                    Game.instance().getPlayer().setMaxJumpHeight(Game.instance().getPlayer().getMaxJumpHeight() - jumpChange);
                })).play();
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
