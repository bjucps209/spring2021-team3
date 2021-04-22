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

    /**
     * gets the type of the colletable and returns a string value of it's type
     * 
     * @return String value of collectable type
     */
    public String getStringType() {

        if (type == CollectableType.Coin) {
            return "Coin";
        } else if (type == CollectableType.FeatherPowerup) {
            return "FeatherPowerup";
        } else if (type == CollectableType.SpeedPowerup) {
            return "SpeedPowerup";
        } else if (type == CollectableType.CoinPowerup) {
            return "CoinPowerup";
        } else if (type == CollectableType.HealthPowerup) {
            return "HealthPowerup";
        }
        return "";
    }
}
