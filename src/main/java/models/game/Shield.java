package models.game;

@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
public class Shield extends SpaceEntity {

    private transient SpaceEntity origin;

    /**
     * Shield constructor.
      * @param player SpaceEntity type
     */
    public Shield(SpaceEntity player) {
        origin = player;
        this.updateLocation();
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
        // do nothing
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
        return "/views/sprites/shield1.png";
    }
}
