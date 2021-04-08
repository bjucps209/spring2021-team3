package model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public class Box {

    public Box() {

        maxYProperty.bind(Bindings.createDoubleBinding(() -> {
            return centerPoint.yProperty().get() + (heightProperty.get() / 2);
        }, centerPoint.yProperty(), heightProperty));

        minYProperty.bind(Bindings.createDoubleBinding(() -> {
            return centerPoint.yProperty().get() - (heightProperty.get() / 2);
        }, centerPoint.yProperty(), heightProperty));

        minXProperty.bind(Bindings.createDoubleBinding(() -> {
            return centerPoint.xProperty().get() - (widthProperty.get() / 2);
        }, centerPoint.xProperty(), widthProperty));

        maxXProperty.bind(Bindings.createDoubleBinding(() -> {
            return centerPoint.xProperty().get() + (widthProperty.get() / 2);
        }, centerPoint.xProperty(), widthProperty));

    }

    // Physical properties

    protected int id;
    protected Point centerPoint = new Point();
    protected IntegerProperty widthProperty = new SimpleIntegerProperty();
    protected IntegerProperty heightProperty = new SimpleIntegerProperty();
    protected IntegerProperty maxYProperty = new SimpleIntegerProperty();
    protected IntegerProperty minXProperty = new SimpleIntegerProperty();
    protected IntegerProperty maxXProperty = new SimpleIntegerProperty();
    protected IntegerProperty minYProperty = new SimpleIntegerProperty();


    public int getId() {
        return id;
    }

    public Point centerPoint() {
        return centerPoint;
    }

    public IntegerProperty minXProperty() {
        return minXProperty;
    }

    public IntegerProperty maxXProperty() {
        return maxXProperty;
    }

    public IntegerProperty minYProperty() {
        return minYProperty;
    }

    public IntegerProperty maxYProperty() {
        return maxYProperty;
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
        return minXProperty.get();
    }

    public double getMaxX() {
        return maxXProperty.get();
    }

    public double getMinY() {
        return minYProperty.get();
    }

    public double getMaxY() {
        return maxYProperty.get();
    }

    public boolean contains(Point p) {
        return p.getX() >= getMinX() && p.getX() <= getMaxX() && p.getY() >= getMinY() && p.getY() <= getMaxY();
    }

    public boolean overlaps(Box b) {
        return getMaxX() >= b.getMinX() && getMinX() <= b.getMaxX() && getMaxY() >= b.getMinY() && getMinY() <= b.getMaxY();
    }

    public String getType() {
        return "null";
    }

    public void setType(String readUTF) {
    }

    

}
