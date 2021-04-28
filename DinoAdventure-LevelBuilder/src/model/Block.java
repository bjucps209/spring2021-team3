//-------------------------------------------------------------
//File:   Block.java
//Desc:   Class that holds data for a single block, extends from Box
//-------------------------------------------------------------
package model;

/**
 * Holds an instance of a single block.
 */
public class Block extends Box {

    // Tnitiat default texture
    private String texture = "assets/images/world/ground-2.png";

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
