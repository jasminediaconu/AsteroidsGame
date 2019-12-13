package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public transient boolean isPaused = false;

    public transient boolean soundEffect = false;

    public static int scoreUp = 10000;

    //TODO: make spawn chances increase with a higher score.
    private static final double asteroidSpawnChance = 0.03;
    private static final double hostileSpawnChance = 0.0001;

    private transient AnchorPane anchorPane;
    private transient Scene gameScene;

    private transient List<Bullet> bullets = new ArrayList<>();
    private transient List<Asteroid> asteroids = new ArrayList<>();
    private transient List<SpaceEntity> ufos = new ArrayList<>();

    private transient Player player;

    private transient boolean up = false;
    private transient boolean right = false;
    private transient boolean left = false;
    private transient boolean down = false;
    private transient boolean space = false;
    private transient boolean fkey = false;
    private transient boolean pkey = false;
    private transient boolean skey = false;


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
            } else if (e.getCode() == KeyCode.F) {
                fkey = true;
            } else if (e.getCode() == KeyCode.DOWN) {
                down = true;
            } else if (e.getCode() == KeyCode.P) {
                pkey = true;
            } else if (e.getCode() == KeyCode.S) {
                skey = true;
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
            } else if (e.getCode() == KeyCode.F) {
                fkey = false;
            } else if (e.getCode() == KeyCode.DOWN) {
                down = false;
            } else if (e.getCode() == KeyCode.P) {
                pkey = false;
            } else if (e.getCode() == KeyCode.S) {
                skey = false;
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
                checkButtons();
                if (!isPaused) {
                    onUpdate();
                }
            }
        };
        timer.start();

        return anchorPane;
    }

    /**
     * This method adds a Bullet object when the user press the SPACE key.
     * @param bullet Bullet type
     * @param firedFrom SpaceEntity that fired the bullet
     */
    private void addBullet(Bullet bullet, SpaceEntity firedFrom) {
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
    private void addAsteroid(Asteroid asteroid) {
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

        //checkButtons();

        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.isColliding(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);

                    if (bullet.getOrigin() == player) {
                        player.incrementScore(asteroid.getScore());
                    }

                    anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
                }
            }
        }

        //TODO check if needed to check if player died inside collision checking.

        //check if player collided with an asteroid.
        for (SpaceEntity asteroid: asteroids) {
            if (player.isColliding(asteroid)) {
                player.removeLife();
            }
        }

        //check if player collided with an enemy bullet.
        for (Bullet bullet: bullets) {
            if (bullet.getOrigin() != player && player.isColliding(bullet)) {
                player.removeLife();
            }
        }

        //check if player collided with an enemy ship.
        for (SpaceEntity ufo: ufos) {
            if (player.isColliding(ufo)) {
                player.removeLife();
            }
        }

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::move);
        asteroids.forEach(SpaceEntity::move);
        player.move();
        player.cooldown();
        player.updateInvulnerabilityTime();

        //checks if player is qualified to get a life.
        if (player.getCurrentScore() >= scoreUp) {
            player.addLife();
            player.setCurrentScore(player.getCurrentScore() - scoreUp);
        }

        //checks if player died.
        if (!player.hasLives()) {
            gameEnd();
        }

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
        if (fkey) {
            Random rand = new Random();
            int x = rand.nextInt(screenSize);
            int y = rand.nextInt(screenSize);
            player.setLocation(new Point2D(x, y));
        }
        if (down) {
            player.getShield().activateShield();
        }
        if (pkey) {
            if (!isPaused) {
                isPaused = true;
                //TODO go to Pause menu
            } else {
                isPaused = false;
            }

        }
        if (skey) {
            if (!soundEffect) {
                soundEffect = true;
                //TODO turn on sound
            } else {
                soundEffect = false;
                //TODO turn off sound
            }
        }
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
