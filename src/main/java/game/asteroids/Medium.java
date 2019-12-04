package game.asteroids;

import game.Asteroid;

public class Medium extends Asteroid {

    private static final int maxRotation = 8;
    private static final int maxVelocity = 8;
    public static final int score = 150;

    public Medium() {
        super(maxVelocity, maxRotation);
    }

}
