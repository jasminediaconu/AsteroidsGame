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
     * Updates location to the location of it's origin.
     */
    @Override
    public void updateLocation() {
        setLocation(origin.getLocation());
        setRotation(origin.getRotation());
    }

    /**
     * {@inheritDoc}
     * Empty because move does not need to be checked.
     */
    @Override
    public void checkMove() {
    }

    /**
     * Gets the origin of the shield (the player).
     * @return SpaceEntity origin
     */
    public SpaceEntity getOrigin() {
        return origin;
    }



    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        return "/game/sprites/shield1.png";
    }
}
