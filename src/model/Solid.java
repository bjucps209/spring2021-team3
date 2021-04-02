package model;

public class Solid extends Vector2 {

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMinX() {
        return x - (width / 2);
    }

    public int getMaxX() {
        return x + (width / 2);
    }

    public int getMinY() {
        return y - (height / 2);
    }

    public int getMaxY() {
        return y + (height / 2);
    }

    public boolean contains(Vector2 v) {
        return v.getX() >= getMinX() && v.getX() <= getMaxX() && v.getY() >= getMinY() && v.getY() <= getMaxY();
    }

    public boolean overlaps(Solid s) {
        return getMaxX() >= s.getMinX() && getMinX() <= s.getMaxX() && getMaxY() >= s.getMinY() && getMinY() <= s.getMaxY();
    }

}
