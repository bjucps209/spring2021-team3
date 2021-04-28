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

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class Level {
    // arrayList to hold the enemies in the level
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    // arrayList to hold the goals
    private ArrayList<Goal> goals = new ArrayList<Goal>();

    // arraylist to hold the blocks that make up the map
    private ArrayList<Block> blocks = new ArrayList<Block>();

    // arraylist to hold collectables
    private ArrayList<Collectable> collectables = new ArrayList<Collectable>();

    // holds instance of the player
    private Player player;

    // size of the level
    private int width;
    private int height;

    // Name of the level
    private String levelName;

    // Time properties
    private LongProperty startTimeProperty = new SimpleLongProperty();
    private LongProperty runTimeProperty = new SimpleLongProperty();
    private LongProperty currentTimeProperty = new SimpleLongProperty();
    private LongProperty maxTimeProperty = new SimpleLongProperty();
    private LongProperty remainingTimeProperty = new SimpleLongProperty();
    private LongProperty idleTimeProperty = new SimpleLongProperty();

    // Spawn point where Dino will be placed
    private Point spawnPoint = new Point();

    public Level() {

        player = Game.instance().getPlayer();

        // Setup timer bindings

        runTimeProperty.bind(Bindings.createLongBinding(() -> {
            return currentTimeProperty.get() - (startTimeProperty.get() + idleTimeProperty.get());
        }, currentTimeProperty, startTimeProperty, idleTimeProperty));

        remainingTimeProperty.bind(Bindings.createLongBinding(() -> {
            return maxTimeProperty.get() - runTimeProperty.get();
        }, maxTimeProperty, runTimeProperty));

    }

    // Spawn the player
    public void spawnPlayer() {
        Game.instance().getPlayer().setWidth(50);
        Game.instance().getPlayer().setHeight(54);
        Game.instance().getPlayer().centerPoint().copyFrom(Game.instance().getCurrentLevel().getSpawnPoint());
        Game.instance().getPlayer().setDirection(EntityDirection.RIGHT);
    }

    // Getters/Setters

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Point spawnPoint) {
        this.spawnPoint.copyFrom(spawnPoint);
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

    public LongProperty startTimeProperty() {
        return startTimeProperty;
    }

    public LongProperty runTimeProperty() {
        return runTimeProperty;
    }

    public LongProperty currentTimeProperty() {
        return currentTimeProperty;
    }

    // iterate the time and call a physisc update on each enemy and collectable in
    // the level.
    public void tick() {

        currentTimeProperty.set(System.currentTimeMillis());
        player.tick();
        for (Enemy e : enemies)
            e.tick();
        for (Collectable c : collectables)
            c.tick();
        for (Goal g : goals)
            g.tick();

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
    public Entity findEnemy(int id) {

        for (Enemy enemy : enemies) {
            if (enemy.getId() == id) {
                return enemy;
            }
        }
        return null;
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
     * Find the collectable with the given id
     * 
     * @param id
     * @return Collectable with the give id
     */
    public Collectable findCollectable(int id) {
        for (Collectable c : collectables) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
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
    public ArrayList<Enemy> getEnemies() {
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
     * 
     * @param filename String the name and path of the level to save
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
     * 
     * @param fileName String of the name and path to the level to load
     */
    public void load(String fileName) throws IOException {
        // Set the name of the level
        setLevelName(fileName);
        // clear the level of old items/enemies/blocks
        enemies.clear();
        blocks.clear();
        collectables.clear();
        goals.clear();
        // Load Playermanager instance from filename.dat binary file
        var reader = new DataInputStream(new FileInputStream(fileName)); // Create loader
        // read the size of the level
        int width = reader.readInt();
        int height = reader.readInt();
        // read and update the spawn point

        int spawnX = reader.readInt();
        int spawnY = reader.readInt();
        // read the bumber of goals
        int sizeOfGoals = reader.readInt();
        for (int i = 0; i < sizeOfGoals; ++i) {
            Goal flag = new Goal(reader.readInt() - 40, reader.readInt() - 46);
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
        for (int i = 0; i < sizeOfBlocks; ++i) {
            // iterate over each playing gathering their values
            Block box = new Block();
            box.setId(reader.readInt());
            box.setTexture(reader.readUTF());
            box.centerPoint().setXY(reader.readInt(), reader.readInt());
            box.setWidth(reader.readInt());
            box.setHeight(reader.readInt());
            blocks.add(box);
        }
        // Load colletables
        int sizeOfCollectables = reader.readInt();
        // Iterate through the collectables saving each's data
        for (int i = 0; i < sizeOfCollectables; ++i) {
            String type = reader.readUTF();
            int x = reader.readInt();
            int y = reader.readInt();
            Collectable col = new Collectable(x - 50, y - 25, CollectableType.valueOf(type));

            collectables.add(col);
        }

        // set the spawn point
        setSpawnPoint(new Point(spawnX, spawnY));

        // set the size of the level
        setWidth(width);
        setHeight(height);

        reader.close();
    }

    // This was made for serialization the Game model. This method does not save the play in the Player.
    // THe writer passed by the parameter is the one used in the save method in the game class
    public void serialize(DataOutputStream writer) throws IOException {
        writer.writeInt(width);
        writer.writeInt(height);
        int size = enemies.size();
        writer.writeInt(size);
        for (int i = 0; i < size; i++) {
            if (enemies.get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) enemies.get(i);
                writer.writeInt(enemies.get(i).getId());
                enemy.serialize(writer);
            }
        }

        writer.writeInt(blocks.size());
        for (int i = 0; i < blocks.size(); ++i) {
            writer.writeInt(blocks.get(i).getId());
            writer.writeUTF(blocks.get(i).getTexture());
            writer.writeInt(blocks.get(i).centerPoint().getIntX());
            writer.writeInt(blocks.get(i).centerPoint().getIntY());
            writer.writeInt(blocks.get(i).getWidth());
            writer.writeInt(blocks.get(i).getHeight());
        }
        if (levelName != null) {
            writer.writeUTF(levelName);
        } else {
            writer.writeUTF("None");
        }
        writer.writeLong(startTimeProperty.get());
        writer.writeLong(currentTimeProperty.get());
        writer.writeLong(maxTimeProperty.get());
        writer.writeLong(idleTimeProperty.get());
        writer.writeLong((long) System.currentTimeMillis());

        if (collectables.size() > 0) {
            writer.writeBoolean(true);
            writer.writeInt(collectables.size());
            for (Collectable idem : collectables) {
                writer.writeDouble(idem.centerPoint().getX());
                writer.writeDouble(idem.centerPoint().getY());
                writer.writeInt(idem.getType().ordinal());
                writer.writeInt(idem.getId());
                writer.writeDouble(idem.getXVelocity());
                writer.writeDouble(idem.getYVelocity());
            }
        } else {
            writer.writeBoolean(false);
        }

    }

    // this was made for serialization the Game model. This method loads everything
    // that was saved in the serialize method.
    // The reader passed by the parameter is the one used in the load method in the
    // game class.
    public void deserialize(DataInputStream reader) throws IOException {
        width = reader.readInt();
        height = reader.readInt();
        int entitiesSize = reader.readInt();
        enemies.clear();
        for (int i = 0; i < entitiesSize; i++) {
            int id = reader.readInt();
            Enemy enemy = new Enemy();
            enemies.add(enemy);
            enemy.deserialize(reader);
            enemy.setId(id);
        }

        int surfaceSize = reader.readInt();

        blocks.clear();
        for (int i = 0; i < surfaceSize; i++) {
            int id = reader.readInt();
            Block block = new Block();
            block.setId(id);
            block.setTexture(reader.readUTF());
            block.centerPoint().setXY(reader.readInt(), reader.readInt());
            block.setWidth(reader.readInt());
            block.setHeight(reader.readInt());
            blocks.add(block);
        }

        levelName = reader.readUTF();
        startTimeProperty.set(reader.readLong());
        currentTimeProperty.set(reader.readLong());
        maxTimeProperty.set(reader.readLong());
        long idleTime = reader.readLong();
        long savedTime = reader.readLong();
        idleTimeProperty.set(idleTime + (System.currentTimeMillis() - savedTime));

        if (reader.readBoolean() == true) {
            int size = reader.readInt();
            collectables.clear();
            for (int i = 0; i < size; i++) {
                Collectable idem = new Collectable(reader.readDouble(), reader.readDouble(),
                        CollectableType.values()[reader.readInt()]);
                idem.setId(reader.readInt());
                collectables.add(idem);
                idem.setXVelocity(reader.readDouble());
                idem.setYVelocity(reader.readDouble());
            }
        }

    }
}
