//-------------------------------------------------------------
//File:   Gaol.java
//Desc:   extends entity, has a create method
//-------------------------------------------------------------
package model;

/**
 * Holds instance of a gaol
 */
public class Goal extends Entity {

    /**
     * create a new gold at the given x, y location
     * @param x int
     * @param y int
     */
    public Goal(double x, double y) {
        centerPoint.setXY(x, y);
    }
    
    @Override
    /**
     * If the player collids with the gaol, end the game.
     */
    public void tick() {
        if(Game.instance().getPlayer().overlaps(this)) {
            Game.instance().setState(GameState.LEVEL_WON);
        }
    }

}
