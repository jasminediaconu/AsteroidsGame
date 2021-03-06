package models.game;

import static views.GameScreenView.screenSize;

import controllers.GameScreenController;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class SpaceEntity {

    // JavaFX node
    protected transient Node view;

    // spaceEntity Data.
    private Point2D location = new Point2D(0, 0);
    private Point2D velocity = new Point2D(0, 0);
    private double rotation = 0;
    private double rotationSpeed = 0;
    private boolean alive = true;

    /**
     * function that updates the location and rotation of the spaceEntity,
     * to be called every frame.
     * Cannot be overridden.
     */
    @Generated(message = "")
    public final void move() {
        updateLocation();

        //Can't test this method unless this line is commented out,
        //updateView needs the Node which doesnt work in a test suite.
        updateView();

        //Call a checkMove function implemented by child's,
        //To check if the new position of the spaceEntity is valid.
        //Asteroids and bullets should be removed if out of screen, player should wrap around.
        checkMove();
    }

    /**
     * Standard location update method.
     * Can be overwritten by child classes.
     */
    public void updateLocation() {
        setLocation(getLocation().add(getVelocity()));
        setRotation(getRotation() + getRotationSpeed());
    }

    /**
     * A function to check if the new position of the spaceEntity is valid.
     */
    public abstract void checkMove();

    /**
     * If SpaceEntity is off screen, they wrap around.
     * Else nothing happens.
     */
    public void checkWrapAround() {
        double x = this.getLocation().getX();
        double y = this.getLocation().getY();

        if (x < 0 && y < 0) {
            this.setLocation(new Point2D(screenSize,
                    screenSize));
        } else if (x > screenSize && y > screenSize) {
            this.setLocation(new Point2D(0, 0));
        } else if (x > screenSize) {
            this.setLocation(new Point2D(0, y));
        } else if (y > screenSize) {
            this.setLocation(new Point2D(x, 0));
        } else if (x < 0) {
            this.setLocation(new Point2D(screenSize, y));
        } else if (y < 0) {
            this.setLocation(new Point2D(x, screenSize));
        }
    }


    /**
     * helper function of move, which updates te view of the spaceEntity.
     */
    public final void updateView() {
        getView().setRotate(getRotation());
        getView().setTranslateX(getLocation().getX());
        getView().setTranslateY(getLocation().getY());
    }

    /**
     * A function that makes use of javaFX intersects method.
     *
     * @param other the spaceEntity to check possible collision with.
     * @return boolean collision.
     */
    public boolean isColliding(SpaceEntity other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    /**
     * Method to get the sprite URL of the spaceEntity subclass.
     *
     * @return the sprite URL.
     */
    public abstract String getUrl();

    /**
     * Sets sprite of SpaceEntity with a file path.
     *
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

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
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

    /**
     * Excludes the method move() from test coverage as it cannot be tested.
     */
    @interface Generated {
        String message();
    }
}
