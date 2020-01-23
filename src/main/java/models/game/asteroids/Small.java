package models.game.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.game.Asteroid;

public class Small extends Asteroid {

    private static final String sprite0 =
            "/views/sprites/asteroids/asteroidBrown_small0.png";
    private static final String sprite1 =
            "/views/sprites/asteroids/asteroidBrown_small1.png";

    private static final int maxRotation = 8;
    private static final int maxVelocity = 8;
    private static final int score = 200;

    public Small(Random random) {
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
     * Small asteroids don't split into anything.
     * @return empty list
     */
    @Override
    public List<Asteroid> split() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        Random random = new Random();
        int spriteChoice = random.nextInt(2);
        return getSprite(spriteChoice);
    }

    /**
     * Returns a sprite based on an integer received as a parameter.
     * @param sprite The nr of the sprite to return.
     * @return The url to the sprite's PNG.
     */
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    public String getSprite(int sprite) {
        if (sprite == 0) {
            return sprite0;
        } else {
            return sprite1;
        }
    }
}
