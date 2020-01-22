package models.game.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import models.game.Asteroid;

public class Medium extends Asteroid {

    private static final String sprite0 =
            "/views/sprites/asteroids/asteroidBrown_med0.png";
    private static final String sprite1 =
            "/views/sprites/asteroids/asteroidBrown_med1.png";

    private static final int maxRotation = 4;
    private static final int maxVelocity = 4;
    private static final int score = 150;

    public Medium(Random random) {
        super(maxVelocity, maxRotation, random);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return score;
    }

    @Override
    public List<Asteroid> split() {
        Small sm1 = new Small(new Random());
        Small sm2 = new Small(new Random());
        sm1.setLocation(this.getLocation());
        sm2.setLocation(this.getLocation());

        List<Asteroid> res = new ArrayList<>();
        res.add(sm1);
        res.add(sm2);
        return res;
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
