//-------------------------------------------------------------
//File:   Block.java
//Desc:   Basic class that holds data for a single block
//-------------------------------------------------------------
package model;

public class Block extends Box {

    //initiat default texture
    private String texture = "assets/images/world/ground-2.png";

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
