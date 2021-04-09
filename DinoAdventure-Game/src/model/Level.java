package model;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;

public class Level {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<Block> surfaces = new ArrayList<Block>();
    private int width;
    private int height;
    private String levelName;
    private LongProperty startTimeProperty = new SimpleLongProperty();
    private LongProperty runTimeProperty = new SimpleLongProperty();
    private LongProperty currentTimeProperty = new SimpleLongProperty();
    private LongProperty maxTimeProperty = new SimpleLongProperty();
    private LongProperty remainingTimeProperty = new SimpleLongProperty();
    private Point spawnPoint = new Point();

    public Level() {

        entities.add(Game.instance().getPlayer());
        // TODO: Generate enemies

        runTimeProperty.bind(Bindings.createLongBinding(() -> {
            return currentTimeProperty.get() - startTimeProperty.get();
        }, currentTimeProperty, startTimeProperty));

        remainingTimeProperty.bind(Bindings.createLongBinding(() -> {
            return maxTimeProperty.get() - runTimeProperty.get();
        }, maxTimeProperty, runTimeProperty));

    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint.copyFrom(spawnPoint);
    }

    public long getMaxTime() {
        return maxTimeProperty.get() / 1000;
    }

    public void setMaxTime(long seconds) {
        maxTimeProperty.set(seconds * 1000);
    }

    public LongProperty maxTimeProperty() {
        return maxTimeProperty;
    }

    public LongProperty remainingTimeProperty() {
        return remainingTimeProperty;
    }

    public void tick() {

        currentTimeProperty.set(System.currentTimeMillis());
        for(Entity e : entities) e.tick();

    }

    public LongProperty startTimeProperty() {
        return startTimeProperty;
    }

    public LongProperty runTimeProperty() {
        return runTimeProperty;
    }

    public void recordStartTime() {
        startTimeProperty.set(System.currentTimeMillis());
    }

    public void setStartTime(long time) {
        startTimeProperty.set(time);
    }

    public long getStartTime() {
        return startTimeProperty.get();
    }

    public long getRunTime() {
        return runTimeProperty.get();
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
     * @return Block
     */
    public Block findBox(int id) {
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
    public void addBlock(Block surface) {
        surfaces.add(surface);
    }

    /**
     * Gets the surfaces in the level
     * 
     * @return surfaces
     */
    public ArrayList<Block> getBlocks() {
        return surfaces;
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
    public void save(String filename) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename, false))) {
            //write the size of the level
            writer.writeInt(width); 
            writer.writeInt(height);
            //write how many entities their are
            writer.writeInt(entities.size());
            //Iterate through the entities saving each's data
            for (int i = 0; i < entities.size(); ++i) { 
                writer.writeInt(entities.get(i).getId());
                writer.writeUTF(entities.get(i).getType());
                writer.writeInt(entities.get(i).centerPoint().getIntX());
                writer.writeInt(entities.get(i).centerPoint().getIntY());
                writer.writeInt(entities.get(i).getHeight());
                writer.writeInt(entities.get(i).getWidth());
            }
            //Write how many surfaces there are
            writer.writeInt(surfaces.size());
            //Iterate through the surfaces saving each's data
            for (int i = 0; i < surfaces.size(); ++i) {
                writer.writeInt(surfaces.get(i).getId());
                writer.writeUTF(surfaces.get(i).getType());
                writer.writeInt(surfaces.get(i).centerPoint().getIntX());
                writer.writeInt(surfaces.get(i).centerPoint().getIntY());
                writer.writeInt(surfaces.get(i).getWidth());
                writer.writeInt(surfaces.get(i).getHeight());
            }            
        }
    }

    /**
     * 
     * @return
     */
    public Level load(String fileName) throws IOException {
        throw new RuntimeException("Method not implemented");
    }

}
