package models.game;

import models.game.hostiles.LargeUfo;
import models.game.hostiles.SmallUfo;

public abstract class Hostile extends SpaceEntity {

    public Hostile() {
        super();
    }

    /**
     * Method that spawns in a new random UFO.
     * @return A new UFO.
     */
    public static Hostile spawnHostile() {
        double rand = Math.random();
        final double half = 0.5;

        if (rand < half) {
            return new SmallUfo();
        } else {
            return new LargeUfo();
        }
    }
}
