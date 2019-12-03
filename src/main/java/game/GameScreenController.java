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

    private transient List<Bullet> bullets = new ArrayList<Bullet>();
    private transient List<SpaceEntity> asteroids = new ArrayList<>();
    private transient List<SpaceEntity> ufos = new ArrayList<>();

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
        player.setVelocity(new Point2D(0, 0));
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
     * @param firedFrom SpaceEntity that fired the bullet
     */
    private void addBullet(SpaceEntity bullet, SpaceEntity firedFrom) {
        bullets.add((Bullet)bullet);
        double x = firedFrom.getView().getTranslateX() + firedFrom.getView().getTranslateY() / 12;
        double y = firedFrom.getView().getTranslateY() + firedFrom.getView().getTranslateY() / 10;

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
    @SuppressWarnings("PMD") // Warning suppressed because PMD
    private void onUpdate() {                        // flags some for each loops as an UR anomaly
        for (Bullet bullet : bullets) {
            for (SpaceEntity asteroid : asteroids) {
                if (bullet.isColliding(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);

                    if (bullet.getFiredByPlayer()) {
                        //TODO increment score of player
                    }

                    anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
                }
            }
        }

        //check if player collided with an asteroid.
        for (SpaceEntity asteroid: asteroids) {
            if (player.isColliding(asteroid)) {
                player.removeLife();
                //TODO call player.spawn()
            }
        }

        //check if player collided with an enemy bullet.
        for (Bullet bullet: bullets) {
            if (!bullet.getFiredByPlayer() && player.isColliding(bullet)) {
                player.removeLife();
                //TODO call player.spawn()
            }
        }

        for (SpaceEntity ufo: ufos) {
            if (player.isColliding(ufo)) {
                player.removeLife();
                //TODO call player.spawn()
            }
        }

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::moveForward);
        asteroids.forEach(SpaceEntity::moveForward);

        //checks if player is qualified to get a life.
        if (player.getCurrentScore() >= 10000) {
            player.addLife();
            player.setCurrentScore(player.getCurrentScore() - 10000);
        }

        //checks if player died.
        if (!player.hasLives()) {
            gameEnd();
        }

        double threshold = 0.02;

        if (Math.random() < threshold) {
            addAsteroid(new Asteroid(), Math.random() * anchorPane.getPrefWidth(),
                    Math.random() * anchorPane.getPrefHeight());
        }
        player.moveForward();
    }

    /**
     * This method gets called when the player died.
     * Or when the game ends for whatever different reason.
     * Player is prompted to type in an alias.
     * The current game gets added to the database.
     * The highscore/leaderboard screen gets shown(??).
     */
    private void gameEnd() {
        //TODO get alias value
        //TODO add Game to game database
    }

}
