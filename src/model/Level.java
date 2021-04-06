package model;

import java.io.IOException;
import java.util.*;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Level {
    
    private ArrayList<Entity> entities;
    private ArrayList<Box> boxes;
    private DifficultyType difficulty;
    private int width;
    private int height;
    private String levelName;
    private DoubleProperty startTimeProperty = new SimpleDoubleProperty();
    private DoubleProperty runTimeProperty = new SimpleDoubleProperty();

    public Level() {

        entities.add(Game.instance().getPlayer());
        // TODO: Generate enemies

    }

    public void tick() {

        for(Entity e : entities) e.tick();

    }

    public DoubleProperty runTimeProperty() {
        return runTimeProperty;
    }

    public int getRunTimeSeconds() {
        return (int) runTimeProperty.get();
    }

    public void setRunTimeSeconds(double runTime) {
        runTimeProperty.set(runTime);
    }

    /**
     * find the Entity with the given id
     * 
     * @param id
     * @return Entity
     */
    public Entity findEntity(int id) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * get the name of the level
     * 
     * @return - levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * set the name of the level
     * 
     * @param levelName
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * remove the Entity with the id from entities
     * 
     * @param id
     */
    public void removeEntity(int id) {
    }

    /**
     * find the surface with the given id
     * 
     * @param id
     * @return Surface
     */
    public Surface findBox(int id) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * remove the surface with the given surface from surfaces
     * 
     * @param id
     */
    public void removeBox(int id) {
    }

    /**
     * adds entity to entities
     * 
     * @param object
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * adds surface to surfaces
     * 
     * @param object
     */
    public void addBox(Box surface) {
        boxes.add(surface);
    }

    /**
     * set the level's difficulty
     * 
     * @param difficulty - difficulty to set to
     */
    public void setDifficulty(DifficultyType difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * get the difficutly of the level
     * 
     * @return difficulty
     */
    public DifficultyType getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the surfaces in the level
     * 
     * @return surfaces
     */
    public ArrayList<Box> getSurfaces() {
        return boxes;
    }

    /**
     * Gets the Entities in the level
     * 
     * @return surfaces
     */
    public ArrayList<Entity> getEntites() {
        return entities;
    }

    /**
     * get the width of the world
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * set teh width of the world
     * 
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * get the hight of the world
     * 
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the hight of the world
     * 
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * Save the file 
     */
    public void save(String filename) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * 
     * @return
     */
    public Level load(String fileName) throws IOException {
        throw new RuntimeException("Method not implemented");
    }

}
