//-------------------------------------------------------------
//File:   Collectables.java
//Desc:   Extends from entity. Holds methods to create a new 
//        Collectable and get it's type as a string
//-------------------------------------------------------------

package model;

/**
 * Class that holds a single instance of a collectable
 **/
public class Collectable extends Entity {

    // holds the type of collectable
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

    // Getters/setters

    public void setType(CollectableType type) {
        this.type = type;
    }

    public CollectableType getType() {
        return type;
    }
}
