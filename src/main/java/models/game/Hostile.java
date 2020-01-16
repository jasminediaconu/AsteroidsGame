package models.game;

import javafx.geometry.Point2D;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;

public abstract class Hostile extends SpaceEntity {

    private static final transient Point2D spawnPoint1 = new Point2D(0, 0);
    private static final transient Point2D spawnPoint2 = new Point2D(800, 800);
    private static final transient int score = 500;

    /**
     * moves Hostiles.
     */
    public abstract void action();

    /**
     * Method that spawns in a new random UFO.
     * @return A new UFO.
     */
    public static Hostile spawnHostile() {

        Hostile hostile;

        if (Math.round(Math.random()) == 0) {
            if (Math.round(Math.random()) == 0) {
                hostile = new Juggernaut(spawnPoint1);
            } else {
                hostile = new Juggernaut(spawnPoint2);
            }
        } else {
            if (Math.round(Math.random()) == 0) {
                hostile = new Sniper(spawnPoint1);
            } else {
                hostile = new Sniper(spawnPoint2);
            }
        }

        return hostile;
    }

    public static int getScore() {
        return score;
    }
}
