package model;

import javafx.beans.property.*;

public interface Living {

    public int getHealth();

    public void setHealth(int health);

    public IntegerProperty getHealthProperty();
    
}
