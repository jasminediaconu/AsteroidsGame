package game;

import java.util.List;

public class AsteroidIterator implements Iterator {

    private transient List<Asteroid> list;
    private transient int position;
    private transient Class type;

    /**
     * Constructor.
     * @param items list on which to iterate
     */
    public AsteroidIterator(List<Asteroid> items) {
        this.list = items;
        this.position = 0;
    }

    /**
     * Constructor with filter parameter.
     * @param items list on which to iterate and what kind of asteroid to filter
     */
    public AsteroidIterator(List<Asteroid> items, Class type) {
        this.list = items;
        this.position = 0;
        this.type = type;
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
    public Asteroid next() {
        if (!this.hasNext()) {
            return null;
        }
        Asteroid res = list.get(position);
        position++;

        if (type == null) {
            return res;
        }

        if (res.getClass() == this.type) {
            return res;
        } else {
            return next();
        }
    }
}
