package game.hostiles;

import game.Hostile;
import javafx.geometry.Point2D;

public class Juggernaut extends Hostile {

    public Juggernaut() {
        setLocation(new Point2D(400, 400));
    }

    /**
     * adds a random value to velocity and rotationSpeed
     */
    public void thrust() {

        double acceleration = (Math.random() * ((0.01 - - 0.01) + 1)) - 0.01;
        double rotation = (Math.random() * ((0.01 - - 0.01) + 1)) - 0.01;

        setVelocity(getVelocity().add(
                acceleration * Math.cos(Math.toRadians(getRotation())),
                acceleration * Math.sin(Math.toRadians(getRotation()))
        ));

        setRotationSpeed(getRotationSpeed() + rotation);

    }

    @Override
    public void checkMove() {
        //TODO
    }

    @Override
    public String getUrl() {
        return "/game/sprites/enemyBlue1.png";
    }
}

