package game.asteroids;

import game.Asteroid;

public class Large extends Asteroid {

    private static final String sprite0 =
            "/game/sprites/asteroids/asteroidBrown_big0.png";
    private static final String sprite1 =
            "/game/sprites/asteroids/asteroidBrown_big1.png";

    private static final int maxRotation = 4;
    private static final int maxVelocity = 4;
    public static final int score = 100;

    public Large() {
        super(maxVelocity, maxRotation);
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {

        if (Math.round(Math.random()) == 0) {
            return sprite0;
        } else {
            return sprite1;
        }
    }
}
