package game;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class SpaceEntity {
    protected transient Node view;

    protected Point2D velocity = new Point2D(0, 0);

    private boolean alive = true;

    public SpaceEntity(Node view) {
        this.view = view;

    }

    public void moveForward() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the right.
     */
    public void rotateRight() {
        view.setRotate(view.getRotate() + 5);
        //setVelocity(new Point2D(velocity.getX() + Math.cos(Math.toRadians(getRotate())),
        //       velocity.getY() + Math.sin(Math.toRadians(getRotate()))));
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the left.
     */
    public void rotateLeft() {
        view.setRotate(view.getRotate() - 5);
        //setVelocity(new Point2D(velocity.getX() + Math.cos(Math.toRadians(getRotate())),
        //velocity.getY() + Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(SpaceEntity other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    //    protected float speed;
    //    protected SpaceEntity[] spawnables;
    //
    //    int value;      //Point value on destruction
    //
    //    Point2D position, destination,
    //            direction; //int 0-71 for the direction of the SpaceEntity, 0 being directly up.
    //
    //    /**
    //     * Checks whether this SpaceEntity intersects with another.
    //     * @param other The SpaceEntity object to test for intersection with
    //     * @return Boolean that indicates intersection
    //     */
    //    public abstract boolean intersect(SpaceEntity other);
    //
    //    /**
    //     * Get the position of the SpaceEntity
    //     *
    //     * @return
    //     */
    //    public abstract double getPosition();
    //
    //    /**
    //     * Get the distance to the target SpaceEntity by getting
    //     * the difference between its and our
    //     * coordinates and applying the Pythagorean theorem. Uses the objects' centers.
    //     * @param target SpaceEntity that we want to get the distance to.
    //     * @return
    //     */
    //    public abstract double getDistance(SpaceEntity target);
}
