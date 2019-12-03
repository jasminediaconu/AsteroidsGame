package game.asteroids;

import game.Asteroid;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Small extends Asteroid {
    public Small() {
        super(new ImageView(new Image("/game/sprites/meteorBrown_small.png")));
    }
}
