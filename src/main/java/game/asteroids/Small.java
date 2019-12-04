package game.asteroids;

import game.Asteroid;

public class Small extends Asteroid {

    private static final String sprite0 =
            "/game/sprites/asteroids/asteroidBrown_small0.png";
    private static final String sprite1 =
            "/game/sprites/asteroids/asteroidBrown_small1.png";

    private static final int maxRotation = 16;
    private static final int maxVelocity = 16;
    public static final int score = 200;

    public Small() {
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
