package model;

public interface GameObserver {

    public void update();

    public void handleInput();

    public void playSound(String cause);

}
