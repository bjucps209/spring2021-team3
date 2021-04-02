package model;

import java.util.*;

public class Level {
    
    private ArrayList<Entity> entities;
    private ArrayList<Surface> surfaces;

    public Level() {

        entities.add(Game.getInstance().getPlayer());
        // TODO: Generate enemies

    }

    public void tick() {

        for(Entity e : entities) e.tick();

    }

}
