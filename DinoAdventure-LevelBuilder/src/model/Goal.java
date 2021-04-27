//-------------------------------------------------------------
//File:   Gaol.java
//Desc:   extends entity, has a create method
//-------------------------------------------------------------
package model;

public class Goal extends Entity {

    /**
     * create a new gold at the given x, y location
     * @param x int
     * @param y int
     */
    public Goal(double x, double y) {
        centerPoint.setXY(x, y);
    }

}
