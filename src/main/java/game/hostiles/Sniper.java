package game.hostiles;

import game.Hostile;
import javafx.geometry.Point2D;

public class Sniper extends Hostile {

    public Sniper() {
        setLocation(new Point2D(400, 400));
    }


    @Override
    public void checkMove() {
        //TODO
    }

    @Override
    public String getUrl() {
        return "/game/sprites/enemyBlue1.png";
    }
}

