package game;

import javafx.geometry.Point2D;

public class Player extends SpaceEntity {

    private static final int center = GameScreenController.screenSize / 2;

    Player() {
        super();
    }

    /**
     * Spawns the player in the middle of the screen,
     * with a velocity and rotation of 0.
     * @param entity the player.
     */
    public void spawn(SpaceEntity entity) {
        setVelocity(new Point2D(0, 0));
        setLocation(new Point2D(center, center));
        setRotation(0);
        setAlive(true);
        //TODO: make invulnerable
    }

    /**
     * Thrust spaceship.
     */
    public void thrust() {
        setVelocity(new Point2D(
                0.7 * (getVelocity().getX() + Math.cos(Math.toRadians(getRotation()))),
                0.7 * (getVelocity().getY() + Math.sin(Math.toRadians(getRotation()))))
        );

        view.setTranslateX(view.getTranslateX() + getVelocity().getX());
        view.setTranslateY(view.getTranslateY() + getVelocity().getY());
        System.out.println(getVelocity().getX() + " : " + getVelocity().getY());
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the right.
     */
    public void rotateRight() {
        setRotation(getRotation() + 5);
        //setVelocity(new Point2D(velocity.getX() + Math.cos(Math.toRadians(getRotate())),
        //       velocity.getY() + Math.sin(Math.toRadians(getRotate()))));
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the left.
     */
    public void rotateLeft() {
        setRotation(getRotation() - 5);
        //setVelocity(new Point2D(velocity.getX() + Math.cos(Math.toRadians(getRotate())),
        //velocity.getY() + Math.sin(Math.toRadians(getRotate()))));
    }

    @Override
    public void checkMove() {

    }

}