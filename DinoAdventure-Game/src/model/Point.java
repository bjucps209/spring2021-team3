package model;

import javafx.beans.property.*;

public class Point {

    public Point() { }

    public Point(double x, double y) {
        xProperty.set(x);
        yProperty.set(y);
    }

    public Point(Point p) {
        xProperty.set(p.getX());
        yProperty.set(p.getY());
    }

    // Physical properties

    protected DoubleProperty xProperty = new SimpleDoubleProperty();
    protected DoubleProperty yProperty = new SimpleDoubleProperty();

    public int getIntX() {
        return (int) xProperty.get();
    }

    public int getIntY() {
        return (int) yProperty.get();
    }

    public double getX() {
        return xProperty.get();
    }

    public void setX(double x) {
        xProperty.set(x);
    }

    public void setX(int x) {
        xProperty.set(x);
    }

    public double getY() {
        return yProperty.get();
    }

    public void setY(double y) {
        yProperty.set(y);
    }

    public void setY(int y) {
        yProperty.set(y);
    }

    public DoubleProperty xProperty() {
        return xProperty;
    }

    public DoubleProperty yProperty() {
        return yProperty;
    }

    public void copyFrom(Point p) {
        xProperty.set(p.getX());
        yProperty.set(p.getY());
    }

    public void setXY(double x, double y) {
        xProperty.set(x);
        yProperty.set(y);
    }

    public void setXY(int x, int y) {
        xProperty.set(x);
        yProperty.set(y);
    }

    public void setXY(int x, double y) {
        xProperty.set(x);
        yProperty.set(y);
    }

    public void setXY(double x, int y) {
        xProperty.set(x);
        yProperty.set(y);
    }

    public void add(Point p) {
        xProperty.set(xProperty.get() + p.getX());
        yProperty.set(yProperty.get() + p.getY());
    }

    public void add(int num) {
        xProperty.set(xProperty.get() + num);
        yProperty.set(yProperty.get() + num);
    }

    public void add(double num) {
        xProperty.set(xProperty.get() + num);
        yProperty.set(yProperty.get() + num);
    }

    public void add(int x, int y) {
        xProperty.set(xProperty.get() + x);
        yProperty.set(yProperty.get() + y);
    }

    public void add(double x, double y) {
        xProperty.set(xProperty.get() + x);
        yProperty.set(yProperty.get() + y);
    }

    public void add(int x, double y) {
        xProperty.set(xProperty.get() + x);
        yProperty.set(yProperty.get() + y);
    }

    public void add(double x, int y) {
        xProperty.set(xProperty.get() + x);
        yProperty.set(yProperty.get() + y);
    }

    public void subtract(Point p) {
        xProperty.set(xProperty.get() - p.getX());
        yProperty.set(yProperty.get() - p.getY());
    }

    public void subtract(int num) {
        xProperty.set(xProperty.get() - num);
        yProperty.set(yProperty.get() - num);
    }

    public void subtract(double num) {
        xProperty.set(xProperty.get() - num);
        yProperty.set(yProperty.get() - num);
    }

    public void subtract(int x, int y) {
        xProperty.set(xProperty.get() - x);
        yProperty.set(yProperty.get() - y);
    }

    public void subtract(double x, double y) {
        xProperty.set(xProperty.get() - x);
        yProperty.set(yProperty.get() - y);
    }

    public void subtract(int x, double y) {
        xProperty.set(xProperty.get() - x);
        yProperty.set(yProperty.get() - y);
    }

    public void subtract(double x, int y) {
        xProperty.set(xProperty.get() - x);
        yProperty.set(yProperty.get() - y);
    }

    public void multiply(Point p) {
        xProperty.set(xProperty.get() * p.getX());
        yProperty.set(yProperty.get() * p.getY());
    }

    public void multiply(int num) {
        xProperty.set(xProperty.get() * num);
        yProperty.set(yProperty.get() * num);
    }

    public void multiply(double num) {
        xProperty.set(xProperty.get() * num);
        yProperty.set(yProperty.get() * num);
    }

    public void multiply(int x, int y) {
        xProperty.set(xProperty.get() * x);
        yProperty.set(yProperty.get() * y);
    }

    public void multiply(double x, double y) {
        xProperty.set(xProperty.get() * x);
        yProperty.set(yProperty.get() * y);
    }

    public void multiply(double x, int y) {
        xProperty.set(xProperty.get() * x);
        yProperty.set(yProperty.get() * y);
    }

    public void multiply(int x, double y) {
        xProperty.set(xProperty.get() * x);
        yProperty.set(yProperty.get() * y);
    }

    public void divide(Point p) {
        xProperty.set(xProperty.get() / p.getX());
        yProperty.set(yProperty.get() / p.getY());
    }

    public void divide(int num) {
        xProperty.set(xProperty.get() / num);
        yProperty.set(yProperty.get() / num);
    }

    public void divide(double num) {
        xProperty.set(xProperty.get() / num);
        yProperty.set(yProperty.get() / num);
    }

    public void divide(int x, int y) {
        xProperty.set(xProperty.get() / x);
        yProperty.set(yProperty.get() / y);
    }

    public void divide(double x, double y) {
        xProperty.set(xProperty.get() / x);
        yProperty.set(yProperty.get() / y);
    }

    public void divide(double x, int y) {
        xProperty.set(xProperty.get() / x);
        yProperty.set(yProperty.get() / y);
    }

    public void divide(int x, double y) {
        xProperty.set(xProperty.get() / x);
        yProperty.set(yProperty.get() / y);
    }

    public double distanceFrom(Point p) {
        return Math.sqrt(Math.pow(xProperty.get() - p.getX(), 2) + Math.pow(yProperty.get() - p.getY(), 2));
    }

}
