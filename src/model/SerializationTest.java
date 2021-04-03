//-----------------------------------------------------------
//File:   SerializationTest.java
//Desc:   This test the save and load methods in the Game model class.
//----------------------------------------------------------- 
package model;

import org.junit.Test;
import static org.junit.Assert.*;

//Tests will fail because methods are not implemented.
public class SerializationTest {

    private String filename = "serialization.dat";

    @Test
    public void testGame_Save_successful() throws Exception {
        Game world = Game.getInstance();
        Player player = world.getPlayer();
        player.setState(PlayerState.RUNNING);
        world.load(filename);
        
    }

    @Test
    public void testGame_Load_setToWorld() throws Exception{
        Game world = Game.getInstance();
        Player player = world.getPlayer();
        world.load(filename);
        assertEquals(PlayerState.RUNNING, player.getState());
        assertEquals(GameState.LEVEL_PLAYING, world.getState());

    }
}