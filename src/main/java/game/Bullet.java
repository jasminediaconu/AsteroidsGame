package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends SpaceEntity {

    /**
     * Speed of the bullet relative to the shooter.
     */
    private transient double velocity = 5.0;

    /**
     * Constructor for a Bullet.
     * @param firedFrom SpaceEntity that fired the bullet
     */                     // maybe add a param velocity here
    public Bullet(SpaceEntity firedFrom) {
        super(new ImageView(new Image("/game/sprites/laserBlue.png")));
        if (firedFrom instanceof Hostile) {
            this.setImage("/game/sprites/laserGreen.png");
        }
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(firedFrom.getRotate())),
                Math.sin(Math.toRadians(firedFrom.getRotate())))
                .normalize().multiply(5));

        this.getView().setRotate(firedFrom.getRotate());
        this.getView().setRotate(getView().getRotate() + 90);
    }

    /**
     * Sets the bullet velocity.
     * @param velocity new velocity
     */
    public void setBulletVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Getter for bullet velocity.
     * @return current bullet velocity
     */
    public double getBulletVelocity() {
        return this.velocity;
    }

}
