package models.game.hostiles;

import models.game.Hostile;
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
        return "/views/sprites/enemyBlue1.png";
    }
}

