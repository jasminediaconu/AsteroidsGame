package game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import game.hostiles.LargeUfo;
import game.hostiles.SmallUfo;

public abstract class Hostile extends SpaceEntity {

    public Hostile() {
        super();
    }

    /**
     * Method that spawns in a new random UFO.
     * @return A new UFO.
     */
    public static Hostile spawnHostile() {
        double rand = Math.random();

        if(rand < 0.5) {
            return new SmallUfo();
        }
        else {
            return new LargeUfo();
        }

    }
}
