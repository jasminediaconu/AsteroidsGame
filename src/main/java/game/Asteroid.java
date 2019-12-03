package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Asteroid extends SpaceEntity {
    Asteroid() {
        super(new ImageView(new Image("/game/sprites/meteorBrown_small.png")));
        rotationSpeed = (int)(Math.random() * 15) - 7;
    }
}