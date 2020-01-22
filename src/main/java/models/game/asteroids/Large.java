package models.game.asteroids;

import java.util.Random;

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
    private static final int score = 100;

    public Large(Random random) {
        super(maxVelocity, maxRotation, random);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        Random random = new Random();
        int spriteChoice = random.nextInt(4);
        return getSprite(spriteChoice);
    }

    /**
     * Returns a sprite based on an integer received as a parameter.
     *
     * @param sprite The nr of the sprite to return.
     * @return The url to the sprite's PNG.
     */
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public String getSprite(int sprite) {
        if (sprite == 0) {
            return sprite0;
        } else if (sprite == 1) {
            return sprite1;
        } else if (sprite == 2) {
            return sprite2;
        } else {
            return sprite3;
        }
    }
}
