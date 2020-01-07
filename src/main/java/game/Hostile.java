package game;

import game.hostiles.Juggernaut;
import game.hostiles.Sniper;

public abstract class Hostile extends SpaceEntity {



    /**
     * adds a random value to velocity and rotationSpeed
     */
    public void thrust() {


        double acceleration = (Math.random() * ((0.0000001 - - 0.0000001) + 1)) - 0.0000001;
        double rotation = (Math.random() * ((0.0001 - - 0.0001) + 1)) - 0.0001;

        if (Math.random() > 0.1) {
            acceleration = 0;
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
