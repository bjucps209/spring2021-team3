//-------------------------------------------------------------
//File:   Living.java
//Desc:   Interface that holds 3 essential methods for a living entity.
//-------------------------------------------------------------

package model;

import javafx.beans.property.*;

public interface Living {

    //Getters and setters for health
    public int getHealth();

    public void setHealth(int health);

    public IntegerProperty healthProperty();
    
}
