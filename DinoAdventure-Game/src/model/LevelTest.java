package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {



    @Test 

    public void test_level_findEntity() {
        Level level = new Level();

        Enemy enemy = new WanderingEnemy();

        enemy.setId(3);

        level.addEntity(enemy);
        assertEquals(enemy, level.findEntity(3));
    }

    @Test 

    public void test_level_findBox() {
        Level level = new Level();

        Block block = new Block();

        block.setId(5);

        level.addBlock(block);
        assertEquals(block, level.findBox(5));
    }


    @Test
    public void test_Level_Save_Load() throws Exception {
        Level level = new Level();

        Enemy enemy = new WanderingEnemy();
        enemy.setId(2);
        enemy.centerPoint().setXY(10, 25);

        Block block = new Block();
        block.setId(1);
        block.centerPoint().setX(100);
        block.centerPoint().setY(200);
        block.setWidth(100);
        block.setHeight(100);

        //add new elements to the level
        level.addBlock(block);
        level.addEntity(enemy);
        
        level.setLevelName("Custom1");
        level.setHeight(500);
        level.setWidth(2000);

        //save the level
        level.save("Custom1.dat");

        //load the level
        Level level2 = new Level();
        level2.load("Custom1.dat");

        Entity enemy2 = level2.findEntity(2);
        Box block2 = level2.findBox(1);

        //test the loaded level
        assertEquals("Custom1", level2.getLevelName());
        assertEquals(500, level2.getHeight());
        assertEquals(2000, level2.getWidth());

        //ensure the entities loaded properly
        assertEquals(10, enemy2.centerPoint().getX());
        assertEquals(25, enemy2.centerPoint().getY());

        //Ensure the Boxes loaded properly
        assertEquals(1, block2.getId());
        assertEquals(100, block2.centerPoint().getX());
        assertEquals(200, block2.centerPoint().getY());
        assertEquals(100, block2.getWidth());
        assertEquals(100, block2.getHeight());
    }
}
