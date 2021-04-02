package model;

import java.util.*;

public class Level {
    
    private Game game;
    private ArrayList<Entity> entities;
    private ArrayList<Ground> ground;

    public Level(Game game) {
        this.game = game;
        game.getPlayer().setLevel(this);
        entities.add(game.getPlayer());
    }

    public Game getGame() {
        return game;
    }

}
