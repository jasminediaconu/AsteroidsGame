package game;

import game.hostiles.Juggernaut;
import game.hostiles.Sniper;

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

        if(rand < 0.5) {
            return new Sniper();
        }
        else {
            return new Juggernaut();
        }

    }
}
