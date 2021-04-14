package model;

public interface GameObserver {

    public void update();

    public void handleInput();

    public void spawnPlayer(double x, double y);

    public void spawnEnemy(double x, double y, EnemyState type);

}
