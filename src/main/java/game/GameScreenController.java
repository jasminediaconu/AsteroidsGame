package game;

import game.asteroids.Large;
import game.asteroids.Medium;
import game.asteroids.Small;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * The type GameScreen ViewController.
 */
public class GameScreenController {

    public static final int screenSize = 800;

    private transient AnchorPane anchorPane;

    private transient List<SpaceEntity> bullets = new ArrayList<>();
    private transient List<SpaceEntity> asteroids = new ArrayList<>();

    private transient Player player;

    private transient Scene gameScene;

    /**
     * GameScreenController constructor.
     */
    public GameScreenController() {
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(screenSize, screenSize);
        gameScene = new Scene(createContent());
        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet(player);
                addBullet(bullet, player);

                // -------This makes the static ufo shoot a bullet when space is pressed----------
                /*
                Bullet b2  = new Bullet(ufo);
                addBullet(b2, ufo);
                 */
                // -------------------------------------------------------------------------------

            } else if (e.getCode() == KeyCode.UP) {
                player.thrust();
            } else  {
                player.move();
            }
        });
    }

    /**
     * Getter for the Game Scene.
     * @return gameScene Scene type
     */
    public Scene getGameScene() {
        return gameScene;
    }

    /**
     * Getter for the Screen size.
     * @return int Screen size
     */
    public int getScreenSize() {
        return screenSize;
    }

    /**
     * Sets up the initial scene of the game.
     * @return
     */
    private Parent createContent() {

        anchorPane.setStyle("-fx-background-image: url('/menu/images/stars.png')");

        player = new Player();
        addSpaceEntity(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return anchorPane;
    }

    /**
     * This method adds a Bullet object when the user press the SPACE key.
     * @param bullet SpaceEntity type
     * @param firedFrom SpaceEntity that fired the bullet
     */
    private void addBullet(SpaceEntity bullet, SpaceEntity firedFrom) {
        bullets.add(bullet);
        double x = firedFrom.getView().getTranslateX() + firedFrom.getView().getTranslateY() / 12;
        double y = firedFrom.getView().getTranslateY() + firedFrom.getView().getTranslateY() / 10;

        bullet.setLocation(new Point2D(x, y));

        addSpaceEntity(bullet);
    }


    /**
     * This method adds an Asteroid object on the screen at random time.
     * @param asteroid SpaceEntity type
     */
    private void addAsteroid(SpaceEntity asteroid) {
        asteroids.add(asteroid);
        addSpaceEntity(asteroid);
    }

    /**
     * This method adds a generic SpaceEntity on the screen.
     * @param object SpaceEntity type
     */
    private void addSpaceEntity(SpaceEntity object) {
        object.setView(new ImageView(new Image(object.getUrl())));
        object.getView().setTranslateX(object.getLocation().getX());
        object.getView().setTranslateY(object.getLocation().getY());
        anchorPane.getChildren().add(object.getView());
    }

    /**
     * This method updates the objects on the screen according to the Timer.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void onUpdate() {
        for (SpaceEntity bullet : bullets) {
            for (SpaceEntity asteroid : asteroids) {
                if (bullet.isColliding(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);

                    anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
                }
            }
        }

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::move);
        asteroids.forEach(SpaceEntity::move);

        double threshold = 0.005;

        if (Math.random() < threshold) {
            addAsteroid(new Small());
        }
        if (Math.random() < threshold) {
            addAsteroid(new Large());
        }
        if (Math.random() < threshold) {
            addAsteroid(new Medium());
        }
        player.move();
    }

}