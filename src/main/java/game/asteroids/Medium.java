package game.asteroids;

import game.Asteroid;

public class Medium extends Asteroid {

    private static final String sprite0 =
            "/game/sprites/asteroids/asteroidBrown_med0.png";
    private static final String sprite1 =
            "/game/sprites/asteroids/asteroidBrown_med1.png";

    private static final int maxRotation = 4;
    private static final int maxVelocity = 4;

    public Medium() {
        super(maxVelocity, maxRotation);
        this.score = 150;
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
