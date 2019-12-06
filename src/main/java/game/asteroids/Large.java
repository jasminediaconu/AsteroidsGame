package game.asteroids;

import game.Asteroid;

public class Large extends Asteroid {

    private static final String sprite0 =
            "/game/sprites/asteroids/asteroidBrown_big0.png";
    private static final String sprite1 =
            "/game/sprites/asteroids/asteroidBrown_big1.png";
    private static final String sprite2 =
            "/game/sprites/asteroids/asteroidBrown_big2.png";
    private static final String sprite3 =
            "/game/sprites/asteroids/asteroidBrown_big3.png";

    private static final int maxRotation = 2;
    private static final int maxVelocity = 2;

    public Large() {
        super(maxVelocity, maxRotation);
        this.score = 100;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {

        if (Math.round(Math.random()) == 0) {
            if (Math.round(Math.random()) == 0) {
                return sprite0;
            } else {
                return sprite1;
            }
        } else {
            if (Math.round(Math.random()) == 0) {
                return sprite2;
            } else {
                return sprite3;
            }
        }
    }
}
