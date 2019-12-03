package game.asteroids;

import game.Asteroid;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Large extends Asteroid{
    public Large() {
        super(new ImageView(new Image("/game/sprites/meteorBrown_big1.png")));
    }
}
