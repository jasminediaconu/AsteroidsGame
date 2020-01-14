package models.game.asteroids;

import models.game.Asteroid;

public class Large extends Asteroid {

    private static final String sprite0 =
            "/views/sprites/asteroids/asteroidBrown_big0.png";
    private static final String sprite1 =
            "/views/sprites/asteroids/asteroidBrown_big1.png";
    private static final String sprite2 =
            "/views/sprites/asteroids/asteroidBrown_big2.png";
    private static final String sprite3 =
            "/views/sprites/asteroids/asteroidBrown_big3.png";

    private static final int maxRotation = 2;
    private static final int maxVelocity = 2;

    public Large() {
        super(maxVelocity, maxRotation);
        this.score = 100;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getScore() {
        return 20;
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
