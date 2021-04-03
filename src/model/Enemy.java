package model;

import java.io.*;

public class Enemy extends LivingEntity {

    protected EnemyState state;
    protected EntityDirection direction = EntityDirection.LEFT;

    public EnemyState getState() {
        return state;
    }

    public void setState(EnemyState state) {
        this.state = state;
    }

    // writes the each property to the DataOutputStream passed in the parameters of the enemy to the file to be saved.
    public void serialize(DataOutputStream writer){
        throw new RuntimeException("The serialize method for Enemy class not implemented");
    }

    // reads the DataOutputStream passed in the parameters and sets the Game model accordingly.
    public void deserialize(DataInput reader){
        throw new RuntimeException("The deserialize method for Enemy class not implemented");
    }

}
