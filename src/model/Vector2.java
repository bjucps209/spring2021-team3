package model;

public class Vector2 {

    public Vector2() { }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        x = v.getX();
        y = v.getY();
    }

    // Physical properties

    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void copyFrom(Vector2 v) {
        x = v.getX();
        y = v.getY();
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 v) {
        x += v.getX();
        y += v.getY();
    }

    public void add(int num) {
        x += num;
        y += num;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void subtract(Vector2 v) {
        x -= v.getX();
        y -= v.getY();
    }

    public void subtract(int num) {
        x -= num;
        y -= num;
    }

    public void subtract(int x, int y) {
        this.x -= x;
        this.y -= y;
    }

    public void multiply(Vector2 v) {
        x = (int) (x * v.getX());
        y = (int) (y * v.getY());
    }

    public void multiply(int num) {
        x = x * num;
        y = y * num;
    }

    public void multiply(int x, int y) {
        this.x = (int) (this.x * x);
        this.y = (int) (this.y * y);
    }

    public void multiply(double num) {
        x = (int) (x * num);
        y = (int) (y * num);
    }

    public void multiply(double x, double y) {
        this.x = (int) (this.x * x);
        this.y = (int) (this.y * y);
    }

    public void divide(Vector2 v) {
        x = (int) (x / v.getX());
        y = (int) (y / v.getY());
    }

    public void divide(int num) {
        x = (int) (x / num);
        y = (int) (y / num);
    }

    public void divide(double num) {
        x = (int) (x / num);
        y = (int) (y / num);
    }

    public void divide(int x, int y) {
        this.x = (int) (this.x / x);
        this.y = (int) (this.y / y);
    }

    public void divide(double x, double y) {
        this.x = (int) (this.x / x);
        this.y = (int) (this.y / y);
    }

    public double distanceFrom(Vector2 v) {
        return Math.sqrt(Math.pow(this.x - v.getX(), 2) + Math.pow(this.y - v.getY(), 2));
    }

    public boolean is(Vector2 v) {
        return x == v.getX() && y == v.getY();
    }

}
