package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends SpaceEntity {
    Player() {
        super(new ImageView(new Image("game/sprites/playerShip.png")));
    }
}