package models.game.asteroids;

import java.util.Random;
import models.game.Asteroid;

public class Medium extends Asteroid {

    private static final String sprite0 =
            "/views/sprites/asteroids/asteroidBrown_med0.png";
    private static final String sprite1 =
            "/views/sprites/asteroids/asteroidBrown_med1.png";

    private static final int maxRotation = 4;
    private static final int maxVelocity = 4;

    public Medium(Random random) {
        super(maxVelocity, maxRotation, random);
        this.score = 150;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return 50;
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
