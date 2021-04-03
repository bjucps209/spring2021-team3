package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {



    @Test 

    public void test_level_findEntity() {
        Level level = new Level();

        Enemy enemy = new Enemy();

        enemy.setId(3);

        level.addEntity(enemy);
        assertEquals(enemy, level.findEntity(3));
    }

    @Test 

    public void test_level_findSurface() {
        Level level = new Level();

        Surface block = new Surface();

        block.setId(5);

        level.addSurface(block);
        assertEquals(block, level.findSurface(5));
    }


    @Test
    public void test_Level_Save_Load() throws Exception {
        Level level = new Level();

        Enemy enemy = new Enemy();
        enemy.setId(2);
        enemy.setXY(10, 25);

        Surface block = new Surface();
        block.setId(1);
        block.setX(100);
        block.setY(200);
        block.setWidth(100);
        block.setHeight(100);

        //add new elements to the level
        level.addSurface(block);
        level.addEntity(enemy);
        
        level.setLevelName("Custom1");
        level.setDifficulty(DifficultyType.HARD);
        level.setHeight(500);
        level.setWidth(2000);

        //save the level
        level.save();

        //load the level
        Level level2 = new Level();
        level2.load("Custom1.dat");

        Entity enemy2 = level2.findEntity(2);
        Surface block2 = level2.findSurface(1);

        //test the loaded level
        assertEquals("Custom1", level2.getLevelName());
        assertEquals(DifficultyType.HARD, level2.getDifficulty());
        assertEquals(500, level2.getHeight());
        assertEquals(2000, level2.getWidth());

        //ensure the entities loaded properly
        assertEquals(10, enemy2.getX());
        assertEquals(25, enemy2.getY());

        //Ensure the surface loaded properly
        assertEquals(1, block2.getId());
        assertEquals(100, block2.getX());
        assertEquals(200, block2.getY());
        assertEquals(100, block2.getWidth());
        assertEquals(100, block2.getHeight());
    }
}