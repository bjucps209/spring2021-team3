//---------------------------------------------------------------
//File:   LevelDesigner.jave
//Desc:   Singleton model class that holds a level
//Creator: Christopehr Zuehlke
//---------------------------------------------------------------

package model;

public class LevelDesigner {

    //Holdes the current level
    Level level;

    /**
     * set the level to the given
     * 
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Get the current level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * reset the designer
     */
    public static void reset() {
        instance = new LevelDesigner();
    }

    // Singleton implementation
    // prevent direct instantiation outside this class

    private LevelDesigner() {
        this.level = new Level();
    }

    private static LevelDesigner instance = new LevelDesigner();

    public static LevelDesigner instance() {
        return instance;
    }

    

}
