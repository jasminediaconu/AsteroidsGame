package game;

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

        updateView();

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
            this.setLocation(new Point2D(GameScreenController.screenSize,
                    GameScreenController.screenSize));
        } else if (x > GameScreenController.screenSize && y > GameScreenController.screenSize) {
            this.setLocation(new Point2D(0, 0));
        } else if (x > GameScreenController.screenSize) {
            this.setLocation(new Point2D(0, y));
        } else if (y > GameScreenController.screenSize) {
            this.setLocation(new Point2D(x, 0));
        } else if (x < 0) {
            this.setLocation(new Point2D(GameScreenController.screenSize, y));
        } else if (y < 0) {
            this.setLocation(new Point2D(x, GameScreenController.screenSize));
        }
    }


    /**
     * helper function of move, which updates te view of the spaceEntity.
     */
    public final void updateView() {
        getView().setTranslateX(getLocation().getX());
        getView().setTranslateY(getLocation().getY());
        getView().setRotate(getRotation());
    }

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
