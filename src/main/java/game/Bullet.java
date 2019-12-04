package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends SpaceEntity {

    /**
     * Speed of the bullet relative to the shooter.
     */
    private transient double speed = 5.0;

    /**
     * Constructor for a Bullet.
     * @param firedFrom SpaceEntity that fired the bullet
     */                     // maybe add a param velocity here
    public Bullet(SpaceEntity firedFrom) {
        super();
        if (firedFrom instanceof Hostile) {
            this.setImage("/game/sprites/laserGreen.png");
        }
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(firedFrom.getRotation())),
                Math.sin(Math.toRadians(firedFrom.getRotation())))
                .normalize().multiply(5));

        this.getView().setRotate(firedFrom.getRotation());
        this.getView().setRotate(getView().getRotate() + 90);
    }

    /**
     * Sets the bullet speed.
     * @param speed new speed
     */
    public void setBulletVelocity(double speed) {
        this.speed = speed;
    }

    /**
     * Getter for bullet velocity.
     * @return current bullet velocity
     */
    public double getBulletVelocity() {
        return this.speed;
    }

    @Override
    public void checkMove() {

    }
}
