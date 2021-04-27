//-------------------------------------------------------------
//File:   Collectables.java
//Desc:   Extends from entity. Holds methods to create a new 
//        Collectable and get it's type as a string
//-------------------------------------------------------------

package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class Collectable extends Entity {

    //Holds the type of collectable
    private CollectableType type;

    /**
     * Create a new Collectables at x, y and of CollectableType type
     * 
     * @param x
     * @param y
     * @param type
     */
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

    /**
     * Method to collect for player to collect the item and play the sound fx
     */
    public void collect() {

        //length of soundFx
        int effectSeconds = 10;
        switch(type) {
            case Coin:
                //Play the sound
                Game.instance().observers().forEach(o -> {
                    o.playSound("coin");
                });
                
                //Add the item to the player
                Game.instance().getPlayer().scoreProperty().set(Game.instance().getPlayer().scoreProperty().get() + 25);
                break;

            case FeatherPowerup:
            case SpeedPowerup:
                Game.instance().observers().forEach(o -> {
                    o.playSound("powerup");
                });
                Game.instance().getPlayer().getEffects().put(type, effectSeconds);
                break;

            case HealthPowerup:
                int healthChange = 5;
                Game.instance().observers().forEach(o -> {
                    o.playSound("powerup");
                });
                Game.instance().getPlayer().setHealth(Game.instance().getPlayer().getHealth() + healthChange);
                break;

            case CoinPowerup:
                int scoreChange = 100;
                Game.instance().observers().forEach(o -> {
                    o.playSound("coin");
                    o.playSound("powerup");
                });
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
