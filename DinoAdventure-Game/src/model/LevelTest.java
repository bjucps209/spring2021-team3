//-------------------------------------------------------------
//File:   LevelTest.java
//Desc:   Holds Junit Test methods for level's save/load methods
//-------------------------------------------------------------

package model;

import org.junit.Test;
import static org.junit.Assert.*;

public class LevelTest {



    @Test 

    public void test_level_findEntity() {
        Level level = new Level();

        Enemy enemy = new Enemy();

        enemy.setId(3);

        level.getEnemies().add(enemy);
        assertEquals(enemy, level.findEnemy(3));
    }

    @Test 

    public void test_level_findBox() {
        Level level = new Level();

        Block block = new Block();

        block.setId(5);

        level.getBlocks().add(block);
        assertEquals(block, level.findBlock(5));
    }

    @Test
    public void test_level_methods() throws Exception {
        Level level = new Level();

        Enemy enemy = new Enemy();
        enemy.setType(EnemyState.FLEEING);
        enemy.setId(2);
        enemy.centerPoint().setXY(10, 25);

        Block block = new Block();
        block.setId(1);
        block.centerPoint().setX(100);
        block.centerPoint().setY(200);
        block.setWidth(100);
        block.setHeight(100);

        //add new elements to the level
        level.getBlocks().add(block);
        level.getEnemies().add(enemy);
        
        level.setLevelName("Custom1");
        // level.setDifficulty(DifficultyType.HARD);
        level.setHeight(500);
        level.setWidth(2000);

        //save the level
        level.save("Custom2.dat");
    }

    @Test
    public void test_Level_Save_Load() throws Exception {
        Level level = new Level();

        Enemy enemy = new Enemy();
        enemy.setType(EnemyState.FLEEING);
        enemy.setId(2);
        enemy.centerPoint().setXY(10, 25);

        Block block = new Block();
        block.setId(1);
        block.centerPoint().setX(100);
        block.centerPoint().setY(200);
        block.setWidth(100);
        block.setHeight(100);

        //add new elements to the level
        level.getBlocks().add(block);
        level.getEnemies().add(enemy);
        
        level.setLevelName("Custom1");
        // level.setDifficulty(DifficultyType.HARD);
        level.setHeight(500);
        level.setWidth(2000);

        //save the level
        level.save("Custom1.dat");

        //load the level
        Level level2 = new Level();
        level2.load("Custom1.dat");

        Entity enemy2 = level2.findEnemy(2);
        Block block2 = level2.findBlock(1);

        //test the loaded level
        // assertEquals("Custom1", level2.getLevelName());
        // assertEquals(DifficultyType.HARD, level2.getDifficulty());
        assertEquals(500, level2.getHeight());
        assertEquals(2000, level2.getWidth());

        //ensure the entities loaded properly
        assertEquals(10, (int)enemy2.centerPoint().getX());
        assertEquals(25, (int)enemy2.centerPoint().getY());

        //Ensure the Boxes loaded properly
        assertEquals(1, block2.getId());
        assertEquals(100, (int)block2.centerPoint().getX());
        assertEquals(200, (int)block2.centerPoint().getY());
        assertEquals(100, block2.getWidth());
        assertEquals(100, block2.getHeight());
    }
}
