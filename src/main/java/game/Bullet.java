package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends SpaceEntity {
    Bullet() {
        super(new ImageView(new Image("/game/sprites/laserBlue.png")));
    }
}
