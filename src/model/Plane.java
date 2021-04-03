package model;

public class Plane extends Point {

    public Plane() { }

    public Plane(int x, int y, int width, int height) {
        xProperty.set(x);
        yProperty.set(y);
        this.width = width;
        this.height = height;
    }

    // Physical properties

    private int width;
    private int height;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getMinX() {
        return xProperty.get() - (width / 2);
    }

    public double getMaxX() {
        return xProperty.get() + (width / 2);
    }

    public double getMinY() {
        return yProperty.get() - (height / 2);
    }

    public double getMaxY() {
        return yProperty.get() + (height / 2);
    }

    public boolean contains(Point p) {
        return p.getX() >= getMinX() && p.getX() <= getMaxX() && p.getY() >= getMinY() && p.getY() <= getMaxY();
    }

    public boolean overlaps(Plane p) {
        return getMaxX() >= p.getMinX() && getMinX() <= p.getMaxX() && getMaxY() >= p.getMinY() && getMinY() <= p.getMaxY();
    }

}
