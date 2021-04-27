//-------------------------------------------------------------
//File:   Level.java
//Desc:   Holds a collection of blocks, enemies, collectables, player, and size of level
//        Methodes to save/load a level
//-------------------------------------------------------------
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Level {

    // arrayList to hold the enemies in the level
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    // arrayList to hold the goals
    private ArrayList<Goal> goals = new ArrayList<Goal>();

    // arraylist to hold the blocks that make up the map
    private ArrayList<Block> blocks = new ArrayList<Block>();

    // arraylist to hold collectables
    private ArrayList<Collectable> collectables = new ArrayList<Collectable>();

    // width of the level
    private int width = 0;

    // height of the level
    private int height = 0;

    // Name of the level
    private String levelName;

    // Point object that holds the spawn point
    private Point spawnPoint = new Point();

    public Level() {
    }

    /**
     * get the goal objects of the level
     * 
     * @return Goal
     */
    public ArrayList<Goal> getGoals() {
        return goals;
    }

    /**
     * get the Point objects of the level
     * 
     * @return Point
     */
    public Point getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * set the spawn point for the level
     */
    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint.copyFrom(spawnPoint);
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
     * find the Entity with the given id
     * 
     * @param id
     * @return Entity
     */
    public Enemy findEntity(int id) {
        for (Enemy entity : enemies) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    /**
     * remove the given enemy from entities
     * 
     * @param Enemy entity
     */
    public void removeEntity(Enemy entity) {
        enemies.remove(entity);
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
     * remove the given block from blocks
     * 
     * @param block
     */
    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    /**
     * remove the given item from collectables
     * 
     * @param id
     */
    public void removeCollectable(Collectable item) {
        collectables.remove(item);
    }

    /**
     * remove the given goal from the level
     * 
     * @param flag
     */
    public void removeGoal(Goal flag) {
        goals.remove(flag);
    }

    /**
     * adds enemy to entities
     * 
     * @param entity
     */
    public void addEntity(Enemy entity) {
        enemies.add(entity);
    }

    /**
     * adds surface to surfaces
     * 
     * @param block
     */
    public void addBlock(Block block) {
        blocks.add(block);
    }

    /**
     * adds item to collectables
     * 
     * @param item
     */
    public void addCollectable(Collectable item) {
        collectables.add(item);
    }

    /**
     * Gets the surfaces in the level
     * 
     * @return blocks in the level
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * Gets the Entities in the level
     * 
     * @return surfaces
     */
    public ArrayList<Enemy> getEntites() {
        return enemies;
    }

    /**
     * Gets the items in the level
     * 
     * @return surfaces
     */
    public ArrayList<Collectable> getCollectables() {
        return collectables;
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

            // write the spawn point
            writer.writeInt(spawnPoint.getIntX());
            writer.writeInt(spawnPoint.getIntY());

            // write the number of goals
            writer.writeInt(goals.size());

            // write the coardinates for each Goal
            for (int i = 0; i < goals.size(); ++i) {
                writer.writeInt(goals.get(i).centerPoint().getIntX());
                writer.writeInt(goals.get(i).centerPoint().getIntY());
                writer.writeInt(goals.get(i).getHeight());
                writer.writeInt(goals.get(i).getWidth());
            }

            // write how many entities their are
            writer.writeInt(enemies.size());

            // Iterate through the entities saving each's data
            for (int i = 0; i < enemies.size(); ++i) {
                writer.writeInt(enemies.get(i).getId());
                writer.writeUTF(enemies.get(i).getType().toString());
                writer.writeInt(enemies.get(i).centerPoint().getIntX());
                writer.writeInt(enemies.get(i).centerPoint().getIntY());
            }

            // Write how many blocks there are
            writer.writeInt(blocks.size());

            // Iterate through the blocks saving each's data
            for (int i = 0; i < blocks.size(); ++i) {
                writer.writeInt(blocks.get(i).getId());
                writer.writeUTF(blocks.get(i).getTexture());
                writer.writeInt(blocks.get(i).centerPoint().getIntX());
                writer.writeInt(blocks.get(i).centerPoint().getIntY());
                writer.writeInt(blocks.get(i).getHeight());
                writer.writeInt(blocks.get(i).getWidth());

            }
            // write how many collectables there are
            writer.writeInt(collectables.size());

            // Iterate through the collectables saving each's data
            for (int i = 0; i < collectables.size(); ++i) {

                // save data for each collacetable
                writer.writeUTF(collectables.get(i).getStringType());
                writer.writeInt(collectables.get(i).centerPoint().getIntX());
                writer.writeInt(collectables.get(i).centerPoint().getIntY());
            }
        }
    }

    /**
     * 
     * Load the level
     */
    public void load(String fileName) throws IOException {
        
        // Set the name of the level
        setLevelName(fileName);
        
        // clear the level of old items/enemies/blocks
        enemies.clear();
        blocks.clear();
        collectables.clear();
        goals.clear();
        
        // Create reader to load the level
        var reader = new DataInputStream(new FileInputStream(fileName)); 
        
        // read the size of the level
        int width = reader.readInt();
        int height = reader.readInt();
        
        // read and update the spawn point
        int spawnX = reader.readInt();
        int spawnY = reader.readInt();
        
        // read the bumber of goals
        int sizeOfGoals = reader.readInt();
        for (int i = 0; i < sizeOfGoals; ++i) {
            Goal flag = new Goal(reader.readInt(), reader.readInt());
            flag.setHeight(reader.readInt());
            flag.setWidth(reader.readInt());
            goals.add(flag);
        }

        // read the number of entities
        int sizeOfEntities = reader.readInt();
        // iterate over each playing gathering their values
        for (int i = 0; i < sizeOfEntities; ++i) {

            Enemy entity = new Enemy();
            entity.setId(reader.readInt());
            entity.setType(reader.readUTF());
            entity.centerPoint().setXY(reader.readInt(), reader.readInt());
            entity.setWidth(59);
            entity.setHeight(50);
            enemies.add(entity);
        }

        // get blocks
        int sizeOfBlocks = reader.readInt();
        
        // iterate over block in the save file and create a matching block
        for (int i = 0; i < sizeOfBlocks; ++i) {
            Block box = new Block();
            box.setId(reader.readInt());
            box.setTexture(reader.readUTF());
            box.centerPoint().setXY(reader.readInt(), reader.readInt());
            box.setWidth(reader.readInt());
            box.setHeight(reader.readInt());
            blocks.add(box);
        }
        
        // Load number of collectables
        int sizeOfCollectables = reader.readInt();
        
        // Iterate through the collectables loading each's data
        for (int i = 0; i < sizeOfCollectables; ++i) {
            String type = reader.readUTF();
            int x = reader.readInt();
            int y = reader.readInt();
            Collectable col = new Collectable(x, y, CollectableType.valueOf(type));
            collectables.add(col);
        }

        // set the spawn point
        setSpawnPoint(new Point(spawnX, spawnY));

        // set the size of the level
        setWidth(width);
        setHeight(height);

        //close the reader
        reader.close();
    }

}
