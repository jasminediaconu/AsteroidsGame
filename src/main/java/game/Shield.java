package game;

import javafx.geometry.Point2D;

import javax.annotation.processing.Generated;

public class Shield extends SpaceEntity {

    private transient SpaceEntity origin;

    /**
     * Shield constructor.
      * @param player SpaceEntity type
     */
    public Shield(SpaceEntity player) {
        origin = player;
    }

    /**
     * Overrides SpaceEntity move to take player's movements.
     */
    @Override
    @Generated(message = "")
    public void move() {
        setLocation(origin.getLocation());
        setRotation(origin.getRotation());
        checkMove();

        //Can't test this method unless this line is commented out,
        //updateView needs the Node which doesnt work in a test suite.
        updateView();
    }

    /**
     * Gets the origin of the shield (the player).
     * @return SpaceEntity origin
     */
    public SpaceEntity getOrigin() {
        return origin;
    }

    @Override
    public void checkMove() {

    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        return "/game/sprites/shield1.png";
    }
}
