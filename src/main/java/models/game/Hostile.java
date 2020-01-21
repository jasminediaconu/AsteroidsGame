package models.game;

import javafx.geometry.Point2D;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;

public abstract class Hostile extends SpaceEntity {

    private static final transient Point2D spawnPoint = new Point2D(400, 800);

    /**
     * moves Hostiles.
     */
    public abstract Bullet action();

    /**
     * returns new bullet created from hostile.
     */
    public abstract Bullet shoot();

    /**
     * Method that spawns in a new random UFO.
     * @return A new UFO.
     */
    public static Hostile spawnHostile(Player player) {

        Hostile hostile;

        if (Math.round(Math.random()) == 0) {
            hostile = new Juggernaut(spawnPoint);

        } else {
            hostile = new Sniper(spawnPoint, player);
        }

        return hostile;
    }

    public abstract int getScore();
}
