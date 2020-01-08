package game;

import java.util.List;

public class SpaceEntityIterator implements Iterator {

    private transient List<SpaceEntity> list;
    private transient int position;

    /**
     * Constructor.
     * @param items list on which to iterate
     */
    public SpaceEntityIterator(List<SpaceEntity> items) {
        this.list = items;
        this.position = 0;
    }

    /**
     * Checks if there is an item left in the list it iterates on.
     * @return true iff there are items left, false otherwise
     */
    public boolean hasNext() {
        if (position >= list.size()) {
            return false;
        }
        return true;
    }

    /**
     * Returns the next item in the list, or null is there are none left.
     * @return the next SpaceEntity or null
     */
    public SpaceEntity next() {
        if (!this.hasNext()) {
            return null;
        }
        SpaceEntity res = list.get(position);
        position++;
        return res;
    }

    /**
     * Needed only for testing.
     * Getter for position field.
     * @return position
     */
    public int getPosition() {
        return this.position;
    }
}
