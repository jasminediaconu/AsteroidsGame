package game;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class SpaceEntity {

    //javaFX node
    protected transient Node view;

    //spaceEntity Data.
    private Point2D location = new Point2D(0, 0);
    private Point2D velocity = new Point2D(0, 0);
    private int rotation = 0;
    private int rotationSpeed = 0;
    private boolean alive = true;

    /**
     * function that updates the location and rotation of the spaceEntity,
     * to be called every frame.
     */
    public void move() {
        setLocation(getLocation().add(getVelocity()));
        setRotation(getRotation() + getRotationSpeed());

        //Can't test this method unless this line is commented out,
        //updateView needs the Node which doesnt work in a test suite.
        updateView();

        //TODO: implement checkMove
        //Call a checkMove function implemented by child's,
        //To check if the new position of the spaceEntity is valid.
        //Asteroids and bullets should be removed if out of screen, player should wrap around.
        //checkMove();

    }

    /**
     * helper function of move, which updates te view of the spaceEntity.
     */
    public void updateView() {
        getView().setTranslateX(getLocation().getX());
        getView().setTranslateY(getLocation().getY());
        getView().setRotate(getRotation());
    }

    /**
     * A function to check if the new position of the spaceEntity is valid.
     */
    public abstract void checkMove();

    /**
     * A function that makes use of javaFX intersects method.
     * @param other the spaceEntity to check possible collision with.
     * @return boolean collision.
     */
    public boolean isColliding(SpaceEntity other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    /**
     * Method to get the sprite URL of the spaceEntity subclass.
     * @return the sprite URL.
     */
    public abstract String getUrl();

    /**
     * Sets sprite of SpaceEntity with a file path.
     * @param url link to sprite image.
     */
    public void setImage(String url) {
        this.view = new ImageView(new Image(url));
    }

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
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

}
