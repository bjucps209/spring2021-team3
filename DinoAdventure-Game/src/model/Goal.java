package model;

public class Goal extends Entity {

    public Goal(double x, double y) {
        centerPoint.setXY(x, y);
    }
    
    @Override
    public void tick() {
        if(Game.instance().getPlayer().overlaps(this)) {
            Game.instance().setState(GameState.LEVEL_WON);
        }
    }

}
