package model;

import javafx.beans.property.*;

public class Plane extends Point {

    public Plane() { }

    public Plane(int x, int y, int width, int height) {
        xProperty.set(x);
        yProperty.set(y);
        widthProperty.set(width);
        heightProperty.set(height);
    }

    // Physical properties

    private int id;
    protected IntegerProperty widthProperty = new SimpleIntegerProperty();
    protected IntegerProperty heightProperty = new SimpleIntegerProperty();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IntegerProperty widthProperty() {
        return widthProperty;
    }

    public IntegerProperty heightProperty() {
        return heightProperty;
    }

    public int getWidth() {
        return widthProperty.get();
    }

    public void setWidth(int width) {
        widthProperty.set(width);
    }

    public int getHeight() {
        return heightProperty.get();
    }

    public void setHeight(int height) {
        heightProperty.set(height);
    }

    public double getMinX() {
        return xProperty.get() - (widthProperty.get() / 2);
    }

    public double getMaxX() {
        return xProperty.get() + (widthProperty.get() / 2);
    }

    public double getMinY() {
        return yProperty.get() - (heightProperty.get() / 2);
    }

    public double getMaxY() {
        return yProperty.get() + (heightProperty.get() / 2);
    }

    public boolean contains(Point p) {
        return p.getX() >= getMinX() && p.getX() <= getMaxX() && p.getY() >= getMinY() && p.getY() <= getMaxY();
    }

    public boolean overlaps(Plane p) {
        return getMaxX() >= p.getMinX() && getMinX() <= p.getMaxX() && getMaxY() >= p.getMinY() && getMinY() <= p.getMaxY();
    }

}
