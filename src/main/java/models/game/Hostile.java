package models.game;

import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;

public abstract class Hostile extends SpaceEntity {

    private static final transient double moveThreshold = 0.5;

    /**
     * adds a random value to velocity and rotationSpeed
     */
    public void thrust() {


        double acceleration;
        double rotation = (Math.random() * ((0.0001 - - 0.0001) + 1)) - 0.0001;

        if (Math.random() < moveThreshold) {
            acceleration = 0;
        } else {
            acceleration = (Math.random() * ((0.0000001 - - 0.0000001) + 1)) - 0.0000001;
        }

        setVelocity(getVelocity().add(
                acceleration * Math.cos(Math.toRadians(getRotation())),
                acceleration * Math.sin(Math.toRadians(getRotation()))
        ));

        setRotationSpeed(rotation);
    }

    public Hostile() {
        super();
        setRotation((Math.random() * ((180 - - 180) + 1)) - 180);
    }

    /**
     * Method that spawns in a new random UFO.
     * @return A new UFO.
     */
    public static Hostile spawnHostile() {

        if(Math.round(Math.random()) == 0) {
            return new Sniper();
        }
        else {
            return new Juggernaut();
        }

    }
}
