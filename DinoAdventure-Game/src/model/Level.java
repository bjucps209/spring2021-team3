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

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Collectable> collectables = new ArrayList<Collectable>();
    private Player player;
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


        player = Game.instance().getPlayer();



        // for (int i = 0; i < 10; i++) {
        //     Block block = new Block();
        //     block.setTexture("assets/images/world/ground-" + (i == 0 ? "1" : (i == 9 ? "3" : "2")) + ".png");
        //     block.centerPoint().setXY(100 + (i * 128), 600);
        //     block.setWidth(128);
        //     block.setHeight(128);
        //     addBlock(block);
        // }

        // for (int i = 0; i < 3; i++) {
        //     Block block = new Block();
        //     block.setTexture(("assets/images/world/ground-" + (i == 0 ? "13" : (i == 2 ? "15" : "14")) + ".png"));
        //     block.centerPoint().setXY(500 + (i * 128), 418);
        //     block.setWidth(128);
        //     block.setHeight(93);
        //     addBlock(block);
        // }

    

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
        for (Entity e : enemies)
            e.tick();
        player.tick();

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
    public Entity findEntity(int id) {
        
        for (Entity entity : enemies) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    /**
     * remove the Entity with the id from entities
     * 
     * @param id
     */
    public void removeEntity(Entity entity) {
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

    // /**
    //  * find the surface with the given id
    //  * 
    //  * @param id
    //  * @return Block
    //  */
    // public Collectable findCollectable(int id) {
    //     for (Collectable item : collectables) {
    //         if (item.getId() == id) {
    //             return item;
    //         }
    //     }
    //     return null;
    // }

    /**
     * remove the surface with the given surface id from surfaces
     * 
     * @param id
     */
    public void removeBlock(int id) {
        blocks.remove(findBlock(id));
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
     * adds entity to entities
     * 
     * @param object
     */
    public void addEntity(Enemy entity) {
        enemies.add(entity);
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
     * adds item to collectables
     * 
     * @param object
     */
    public void addCollectable(Collectable item) {
        collectables.add(item);
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
            // write how many entities their are
            writer.writeInt(enemies.size());
            // Iterate through the entities saving each's data
            for (int i = 0; i < enemies.size(); ++i) {
                writer.writeInt(enemies.get(i).getId());
                writer.writeUTF(enemies.get(i).getTypeString());
                writer.writeInt(enemies.get(i).centerPoint().getIntX());
                writer.writeInt(enemies.get(i).centerPoint().getIntY());
                // writer.writeInt(entities.get(i).getHeight());
                // writer.writeInt(entities.get(i).getWidth());
            }
            // Write how many blocks there are
            writer.writeInt(blocks.size());
            // Iterate through the blocks saving each's data
            for (int i = 0; i < blocks.size(); ++i) {
                writer.writeInt(blocks.get(i).getId());
                writer.writeUTF(blocks.get(i).getTexture());
                writer.writeInt(blocks.get(i).centerPoint().getIntX());
                writer.writeInt(blocks.get(i).centerPoint().getIntY());
                writer.writeInt(blocks.get(i).getWidth());
                writer.writeInt(blocks.get(i).getHeight());
            }
            // writer.writeInt(collectables.size());
            // // Iterate through the collectables saving each's data
            // for (int i = 0; i < collectables.size(); ++i) {
            //     // writer.writeInt(collectables.get(i).getId());
            //     writer.writeUTF(collectables.get(i).getType());
            //     writer.writeInt(collectables.get(i).centerPoint().getIntX());
            //     writer.writeInt(collectables.get(i).centerPoint().getIntY());
            // }
        }
    }

    /**
     * 
     * 
     */
    public void load(String fileName) throws IOException {
        enemies.clear();
        blocks.clear();
        collectables.clear();
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
            Enemy entity = new Enemy();
            entity.setId(reader.readInt());
            entity.setType(reader.readUTF());
            entity.centerPoint().setX(reader.readInt());
            entity.centerPoint().setY(reader.readInt());
            entity.setHeight(50);
            entity.setWidth(59);
            enemies.add(entity);

            // int id = reader.readInt();
            // String type = reader.readUTF();
            // int x = reader.readInt();
            // int y = reader.readInt();

            // Enemy entity = new Enemy(x, y, EnemyState.STANDING);
            enemies.add(entity);
        }

        // get blocks
        int sizeOfBlocks = reader.readInt();
        for (int i = 0; i < sizeOfBlocks; ++i) { // iterate over each playing gathering their values
            Block box = new Block();
            box.setId(reader.readInt());
            box.setTexture(reader.readUTF());
            box.centerPoint().setXY(reader.readInt(), reader.readInt());
            box.setWidth(reader.readInt());
            box.setHeight(reader.readInt());
            blocks.add(box);
        }

        setWidth(width);
        setHeight(height);

        reader.close();
    }

    // this was made for serialization the Game model. This method does not save the play in the Player. 
    // THe writer passed by the parameter is the one used in the save method in the game class
    public void serialize(DataOutputStream writer)throws IOException{
        writer.writeInt(width); 
        writer.writeInt(height);
        int size = enemies.size();
        writer.writeInt(size);
         for (int i = 0; i < size; i++) {
            if (enemies.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) enemies.get(i);
                writer.writeInt( enemies.get(i).getId());
                enemy.serialize(writer); 
            }
         }
        
    
        writer.writeInt(blocks.size());
        for (int i = 0; i < blocks.size(); ++i) {
            writer.writeInt( blocks.get(i).getId());
            //writer.writeUTF(surfaces.get(i).getType());
            writer.writeInt( blocks.get(i).centerPoint().getIntX());
            writer.writeInt( blocks.get(i).centerPoint().getIntY());
            writer.writeInt( blocks.get(i).getWidth());
            writer.writeInt( blocks.get(i).getHeight());
        }
        if (levelName != null) {       
            writer.writeUTF(levelName);
        } 
        else{
            writer.writeUTF("None");
        }
        writer.writeLong(currentTimeProperty.longValue());
        writer.writeLong(runTimeProperty.longValue());
        writer.writeLong(maxTimeProperty.longValue());
        writer.writeLong(remainingTimeProperty.longValue());
        writer.writeLong(idleTimeProperty.longValue());

        // if (collectables.size() > 0){
        //     writer.writeBoolean(true);
        //     writer.writeInt(collectables.size());
        //     for (Collectable idem : collectables){
        //         writer.writeInt(idem.getId());
        //         writer.writeUTF(idem.getType());
        //         writer.writeInt(idem.centerPoint().getIntX());
        //         writer.writeInt(idem.centerPoint().getIntY());
        //     }
        // }else{
        //     writer.writeBoolean(false);
        // }
         
    }
            
    // this was made for serialization the Game model. This method loads everything that was saved in the serialize method. 
    // The reader passed by the parameter is the one used in the load method in the game class.
    public void deserialize(DataInputStream reader)throws IOException{
        width = reader.readInt(); 
        height = reader.readInt();
        int entitiesSize = reader.readInt();
        enemies.clear();
         for (int i = 0; i < entitiesSize; i++){   
            int id = reader.readInt();
            Enemy enemy = new Enemy();
            this.addEntity(enemy);
            enemy.deserialize(reader);
            enemy.setId(id);
            }

        int surfaceSize = reader.readInt();

        for (int i = 0; i < surfaceSize; i++) {
            int id = reader.readInt();
            if (this.findBlock(id) != null){
                Block block = this.findBlock(id);
                //String Type = reader.readUTF();
                block.centerPoint().setXY(reader.readInt(), reader.readInt());
                block.setWidth(reader.readInt());
                block.setHeight(reader.readInt());
            }else{
                Block block = new Block();
                block.setId(id);
                //String type = reader.readUTF(); //I do really know what or where this would be set for
                block.centerPoint().setXY(reader.readInt(), reader.readInt());
                block.setWidth(reader.readInt());
                block.setHeight(reader.readInt());
                blocks.add(block);
            }
        }

        levelName = reader.readUTF();  
        currentTimeProperty.setValue(reader.readLong());
        runTimeProperty = new SimpleLongProperty(reader.readLong());
        maxTimeProperty.setValue(reader.readLong());
        remainingTimeProperty = new SimpleLongProperty(reader.readLong());
        idleTimeProperty.setValue(reader.readLong());

    // if (reader.readBoolean() == true){
    //     int size = reader.readInt();
    //     for (int i = 0; i < size; i++){
    //         Collectable idem = collectables.get(i);
    //         idem.setId(reader.readInt());
    //         idem.setType(reader.readUTF());
    //         idem.centerPoint().setIntX(reader.readInt());
    //         idem.centerPoint().setIntY(reader.readInt());
    //     }
    // }

    }
}
