package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class Level {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private int width;
    private int height;
    private String levelName;
    private LongProperty startTimeProperty = new SimpleLongProperty();
    private LongProperty runTimeProperty = new SimpleLongProperty();
    private LongProperty currentTimeProperty = new SimpleLongProperty();
    private LongProperty maxTimeProperty = new SimpleLongProperty();
    private LongProperty remainingTimeProperty = new SimpleLongProperty();
    private LongProperty idleTimeProperty = new SimpleLongProperty();
    private Point spawnPoint = new Point();

    public Level() {

        // Add Player to Level
        entities.add(Game.instance().getPlayer());

        // TODO: Load level

        Block block = new Block();
        block.centerPoint().setXY(100, 600);
        block.setWidth(128);
        block.setHeight(128);
        this.addBlock(block);

        Block block2 = new Block();
        block2.centerPoint().setXY(200, 600);
        block2.setWidth(128);
        block2.setHeight(128);
        this.addBlock(block2);


        // Setup timer bindings

        runTimeProperty.bind(Bindings.createLongBinding(() -> {
            return currentTimeProperty.get() - (startTimeProperty.get() + idleTimeProperty.get());
        }, currentTimeProperty, startTimeProperty, idleTimeProperty));

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

    public LongProperty idleTimeProperty() {
        return idleTimeProperty;
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
        for (Entity entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
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
    public Block findBlock(int id) {
        for (Block block : blocks) {
            if (block.getId() == id) {
                return block;
            }
        }
        return null;
    }

    /**
     * remove the surface with the given surface from surfaces
     * 
     * @param id
     */
    public void removeBlock(int id) {
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
    public void addBlock(Block block) {
        blocks.add(block);
    }

    /**
     * Gets the surfaces in the level
     * 
     * @return surfaces
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
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
            // write the size of the level
            writer.writeInt(width);
            writer.writeInt(height);
            // write how many entities their are
            writer.writeInt(entities.size());
            // Iterate through the entities saving each's data
            for (int i = 0; i < entities.size(); ++i) {
                writer.writeInt(entities.get(i).getId());
                // writer.writeUTF(entities.get(i).getType());
                writer.writeInt(entities.get(i).centerPoint().getIntX());
                writer.writeInt(entities.get(i).centerPoint().getIntY());
                // writer.writeInt(entities.get(i).getHeight());
                // writer.writeInt(entities.get(i).getWidth());
            }
            // Write how many blocks there are
            writer.writeInt(blocks.size());
            // Iterate through the blocks saving each's data
            for (int i = 0; i < blocks.size(); ++i) {
                writer.writeInt(blocks.get(i).getId());
                // writer.writeUTF(blocks.get(i).getType());
                writer.writeInt(blocks.get(i).centerPoint().getIntX());
                writer.writeInt(blocks.get(i).centerPoint().getIntY());
                writer.writeInt(blocks.get(i).getWidth());
                writer.writeInt(blocks.get(i).getHeight());
            }
        }
    }

    /**
     * 
     * @return
     */
    public void load(String fileName) throws IOException {
        setLevelName(fileName);
        // Load Playermanager instance from itmes.dat binary file
        var reader = new DataInputStream(new FileInputStream(fileName)); // Create loader
        // read the size of the level
        int width = reader.readInt();
        int height = reader.readInt();
        // read the number of entities
        int sizeOfEntities = reader.readInt();
        // get how many players there are
        for (int i = 0; i < sizeOfEntities; ++i) { // iterate over each playing gathering their values
            Entity entity = new Enemy() {};
            entity.setId(reader.readInt());
            // reader.readUTF();
            entity.centerPoint().setX(reader.readInt());
            entity.centerPoint().setY(reader.readInt());
            entities.add(entity);
        }
        // get blocks
        int sizeOfBlocks = reader.readInt();
        for (int i = 0; i < sizeOfBlocks; ++i) { // iterate over each playing gathering their values
            Block box = new Block();
            box.setId(reader.readInt());
            // box.setType(reader.readUTF());
            box.centerPoint().setX(reader.readInt());
            box.centerPoint().setY(reader.readInt());
            box.setWidth(reader.readInt());
            box.setHeight(reader.readInt());
            blocks.add(box);
        }

        setWidth(width);
        setHeight(height);

        reader.close();
    }

}
