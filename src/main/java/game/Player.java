package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends SpaceEntity {
    Player() {
        super(new ImageView(new Image("game/sprites/playerShip.png")));
    }

    public void thrust() {

        setVelocity(new Point2D( 0.7 * (velocity.getX() + Math.cos(Math.toRadians(getRotate()))),
                0.7 * (velocity.getY() + Math.sin(Math.toRadians(getRotate())))));

        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
        System.out.println(velocity.getX() + " : " + velocity.getY());
    }
}