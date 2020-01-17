package models.game.asteroids;

import models.game.Asteroid;

public class Small extends Asteroid {

    private static final String sprite0 =
            "/views/sprites/asteroids/asteroidBrown_small0.png";
    private static final String sprite1 =
            "/views/sprites/asteroids/asteroidBrown_small1.png";

    private static final int maxRotation = 8;
    private static final int maxVelocity = 8;

    public Small() {
        super(maxVelocity, maxRotation);
        this.score = 200;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return 100;
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
