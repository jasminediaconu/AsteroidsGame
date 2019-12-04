package game.asteroids;

import game.Asteroid;

public class Large extends Asteroid {

    private static final int maxRotation = 4;
    private static final int maxVelocity = 4;
    public static final int score = 100;

    public Large() {
        super(maxVelocity, maxRotation);
    }

}
