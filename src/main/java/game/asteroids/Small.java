package game.asteroids;

import game.Asteroid;

public class Small extends Asteroid {

    private static final int maxRotation = 16;
    private static final int maxVelocity = 16;
    public static final int score = 200;

    public Small() {
        super(maxVelocity, maxRotation);
    }
}
