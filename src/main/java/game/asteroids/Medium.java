package game.asteroids;

import game.Asteroid;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Medium extends Asteroid{
    public Medium() {
        super(new ImageView(new Image("/game/sprites/meteorBrown_med1.png")));
    }
}
