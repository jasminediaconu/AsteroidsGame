package game;

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

    //TODO: make spawn chances increase with a higher score.
    private static final double asteroidSpawnChance = 0.03;
    private static final double hostileSpawnChance = 0.0001;

    private transient int score = 0;

    private transient AnchorPane anchorPane;
    private transient Scene gameScene;

    private transient List<SpaceEntity> bullets = new ArrayList<>();
    private transient List<SpaceEntity> asteroids = new ArrayList<>();
    private transient Player player;

    private transient boolean up = false;
    private transient boolean right = false;
    private transient boolean left = false;
    private transient boolean down = false;
    private transient boolean space = false;


    /**
     * GameScreenController constructor.
     */
    public GameScreenController() {
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(screenSize, screenSize);
        gameScene = new Scene(createContent());

        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                left = true;
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                right = true;
            } else if (e.getCode() == KeyCode.UP  || e.getCode() == KeyCode.W) {
                up = true;
            } else if (e.getCode() == KeyCode.SPACE) {
                space = true;
            }
        });

        gameScene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                left = false;
            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                right = false;
            } else if (e.getCode() == KeyCode.UP  || e.getCode() == KeyCode.W) {
                up = false;
            } else if (e.getCode() == KeyCode.SPACE) {
                space = false;
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
     * @return The generated parent
     */
    private Parent createContent() {

        anchorPane.setStyle("-fx-background-image: url('/menu/images/stars.png')");

        player = new Player();
        addSpaceEntity(player);

        player.getView().setScaleX(0.69);
        player.getView().setScaleY(0.69);

        AnimationTimer timer = new AnimationTimer() {
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
        addSpaceEntity(bullet);
        double x = firedFrom.getView().getTranslateX() + firedFrom.getView().getTranslateY() / 12;
        double y = firedFrom.getView().getTranslateY() + firedFrom.getView().getTranslateY() / 10;

        bullet.setLocation(new Point2D(x, y));


    }


    /**
     * This method adds an Asteroid object on the screen.
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

        checkButtons();

        for (SpaceEntity bullet : bullets) {
            for (SpaceEntity asteroid : asteroids) {
                if (bullet.isColliding(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);
                    Asteroid ast = (Asteroid)asteroid;
                    score += ast.getScore();

                    anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
                }
            }
        }

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::move);
        asteroids.forEach(SpaceEntity::move);
        player.move();
        player.cooldown();

        if (Math.random() < asteroidSpawnChance) {
            addAsteroid(Asteroid.spawnAsteroid());
        }
    }

    /**
     * Method to call functions that execute behaviour of buttons.
     */
    private void checkButtons() {

        if (up) {
            player.thrust();
        }
        if (right) {
            player.rotateRight();
        }
        if (left) {
            player.rotateLeft();
        }
        if (space && player.canFire()) {
            addBullet(player.shoot(), player);
        }
    }

}