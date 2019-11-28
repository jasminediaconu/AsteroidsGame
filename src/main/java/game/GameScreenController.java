package game;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * The type GameScreen ViewController.
 */
public class GameScreenController {

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
        anchorPane.setPrefSize(800, 800);
        gameScene = new Scene(createContent());
        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.setVelocity(
                        new Point2D(Math.cos(Math.toRadians(player.getRotate())),
                                Math.sin(Math.toRadians(player.getRotate())))
                                .normalize().multiply(5));
                addBullet(bullet,
                        player.getView().getTranslateX() + player.getView().getTranslateY() / 12,
                        player.getView().getTranslateY() + player.getView().getTranslateY() / 10);
            } else if (e.getCode() == KeyCode.UP) {
                player.thrust();
            } else  {
                player.moveForward();
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
     * Sets up the initial scene of the game.
     * @return
     */
    private Parent createContent() {

        player = new Player();
        player.setVelocity(new Point2D(10, 0));
        anchorPane.setStyle("-fx-background-image: url('/menu/images/stars.png')");
        addSpaceEntity(player, 400, 400);

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
     * @param x coordinate
     * @param y coordinate
     */
    private void addBullet(SpaceEntity bullet, double x, double y) {
        bullets.add(bullet);
        addSpaceEntity(bullet, x, y);
    }

    /**
     * This method adds an Asteroid object on the screen at random time.
     * @param asteroid SpaceEntity type
     * @param x coordinate
     * @param y coordinate
     */
    private void addAsteroid(SpaceEntity asteroid, double x, double y) {
        asteroids.add(asteroid);
        addSpaceEntity(asteroid, x, y);
    }

    /**
     * This method adds a generic SpaceEntity on the screen.
     * @param object SpaceEntity type
     * @param x coordinate
     * @param y coordinate
     */
    private void addSpaceEntity(SpaceEntity object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
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

        bullets.forEach(SpaceEntity::moveForward);
        asteroids.forEach(SpaceEntity::moveForward);

        double threshold = 0.02;

        if (Math.random() < threshold) {
            addAsteroid(new Asteroid(), Math.random() * anchorPane.getPrefWidth(),
                    Math.random() * anchorPane.getPrefHeight());
        }
    }

}