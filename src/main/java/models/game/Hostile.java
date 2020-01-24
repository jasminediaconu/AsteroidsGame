package models.game;

import javafx.geometry.Point2D;

public abstract class Hostile extends SpaceEntity {

    protected static final transient Point2D spawnPoint = new Point2D(400, 800);

    /**
     * moves Hostiles.
     */
    public abstract Bullet action();

    /**
     * returns new bullet created from hostile.
     */
    public abstract Bullet shoot();

    public abstract int getScore();
}
