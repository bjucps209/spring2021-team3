package model;

import java.util.*;

public class Level {
    
    private Game game;
    private ArrayList<Entity> entities;
    private ArrayList<Surface> surfaces;

    public Level() {

        entities.add(Game.getInstance().getPlayer());
        // TODO: Generate enemies
        
    }

}
