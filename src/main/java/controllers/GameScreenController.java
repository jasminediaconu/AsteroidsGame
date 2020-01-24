package controllers;

import database.Database;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import models.game.Asteroid;
import models.game.Bullet;
import models.game.Game;
import models.game.Hostile;
import models.game.Player;
import models.game.SpaceEntity;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;
import views.GameScreenView;

/**
 * The type GameScreen ViewController.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class GameScreenController {
    // default values
    public static int scoreUp = 10000;
    private static final double asteroidSpawnChance = 0.01;
    private static final double hostileSpawnChance = 0.003;
    private static final int magicNumber = 1;

    //FXML stuff
    private transient Scene leaderBoardScreen;
    private transient Scene gameScene;
    private transient AnimationTimer timer;
    private transient AnchorPane anchorPane;

    // audio
    private transient AudioController rotateSound = new AudioController();
    private transient AudioController thrustSound = new AudioController();

    // booleans
    public transient boolean isPaused = false;
    public transient boolean isStopped = false;
    public transient boolean soundEffect = false;
    private static boolean pauseScreenInitiated = false;
    private transient boolean isShieldActive = false;

    // SpaceEntity lists
    private transient List<Bullet> bullets = new ArrayList<>();
    private transient List<Asteroid> asteroids = new ArrayList<>();
    private transient List<Hostile> hostiles = new ArrayList<>();

    private transient Player player;

    // key booleans
    private transient boolean up = false;
    private transient boolean right = false;
    private transient boolean left = false;
    private transient boolean down = false;
    private transient boolean space = false;
    private transient boolean fkey = false;
    private transient boolean pkey = false;
    private transient boolean skey = false;

    private transient Robot robot = new Robot();

    private transient GameScreenView gameScreenView;

    /**
     * GameScreenController constructor.
     */
    public GameScreenController() {
        gameScreenView = new GameScreenView(this);

        player = new Player();

        anchorPane = gameScreenView.createContent(player);
        gameScene = new Scene(anchorPane);

        checkKeyPressed(gameScene);
        checkKeyReleased(gameScene);
    }

    /**
     * This method starts the timer that will handle the game updates
     * on the screen, check for the collisions and call the necessary
     * methods that deal with thw game logic.
     */
    public void startGame() {
        timer = new AnimationTimer() {
            public void handle(long now) {
                try {
                    checkButtons();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!isPaused) {
                    onUpdate();
                }
            }
        };

        timer.start();
    }

    /**
     * This method checks if certain keys are pressed during the game.
     *
     * @param scene Scene type
     */
    private void checkKeyPressed(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                left = true;
            } else if (e.getCode() == KeyCode.RIGHT) {
                right = true;
            } else if (e.getCode() == KeyCode.UP) {
                up = true;
            } else if (e.getCode() == KeyCode.SPACE) {
                space = true;
            } else if (e.getCode() == KeyCode.F) {
                fkey = true;
            } else if (e.getCode() == KeyCode.DOWN) {
                down = true;
            } else if ((e.getCode() == KeyCode.P)) {
                pkey = !pkey;
            } else if (e.getCode() == KeyCode.Q) {
                gameEnd(timer);
            }
        });
    }

    /**
     * This method checks if certain keys are released during the game.
     *
     * @param scene Scene type.
     */
    private void checkKeyReleased(Scene scene) {
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                left = false;
            } else if (e.getCode() == KeyCode.RIGHT) {
                right = false;
            } else if (e.getCode() == KeyCode.UP) {
                up = false;
            } else if (e.getCode() == KeyCode.SPACE) {
                space = false;
            } else if (e.getCode() == KeyCode.F) {
                fkey = false;
            } else if (e.getCode() == KeyCode.DOWN) {
                down = false;
            }
        });
    }

    /**
     * Getter for the Game Scene.
     *
     * @return gameScene Scene type
     */
    public Scene getGameScene() {
        return gameScene;
    }

    /**
     * Getter for LeaderBoard Screen scene.
     *
     * @return mainScreen
     */
    public Scene getLeaderBoardScreen() {
        return leaderBoardScreen;
    }

    /**
     * Setter for LeaderBoard Screen Scene.
     *
     * @param scene type Scene
     */
    public void setLeaderBoardScreen(Scene scene) {
        leaderBoardScreen = scene;
    }

    /**
     * This method updates the player lives.
     * When they earn 10,000 points they will earn an extra life.
     * When they collide with another element they will loose a life.
     * @param addLife boolean type
     * @param anchorPane Pane type
     */
    private void updateLives(boolean addLife, Pane anchorPane) {
        if (addLife) {
            player.addLife();
        } else {
            player.removeLife();
            isShieldActive = true;
            gameScreenView.addShield(player, player.activateShield());

            for (Asteroid asteroid : asteroids) {
                asteroid.setAlive(false);
                anchorPane.getChildren().remove(asteroid.getView());
            }
        }
        gameScreenView.setLives(player);
    }

    /**
     * This method updates the objects on the screen according to the Timer.
     */
    private void onUpdate() {
        if (!pauseScreenInitiated) {
            gameScreenView.createPauseScreen();
            pauseScreenInitiated = true;
        }

        if (isStopped) {
            return;
        }

        generateElements(bullets, asteroids, hostiles);

        checkAsteroidCollision(asteroids, player);
        checkBulletCollision(bullets, player, anchorPane);
        checkEnemyCollision(hostiles, player);

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);
        hostiles.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::move);
        asteroids.forEach(SpaceEntity::move);
        hostiles.forEach(Hostile::move);

        checkPlayer(player, asteroids, anchorPane);

        checkSpawnHostile();

        if (Math.random() < asteroidSpawnChance) {
            gameScreenView.addAsteroid(asteroids, Asteroid.spawnAsteroid(Math.random()));
        }

    }

    /**
     * This method takes care of generating elements on the screen.
     * @param bullets List type
     * @param asteroids List type
     * @param hostiles List type
     */
    private void generateElements(List<Bullet> bullets, List<Asteroid> asteroids,
                                  List<Hostile> hostiles) {
        ArrayList<Asteroid> newAsteroids = new ArrayList<>();
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                checkBullet(bullet, asteroid, newAsteroids, anchorPane);
            }

            //check if a player bullet collided with an hostile
            for (Hostile hostile : hostiles) {
                checkEnemy(bullet, hostile, anchorPane);
            }
        }

        for (Asteroid asteroid : newAsteroids) {
            gameScreenView.addAsteroid(asteroids, asteroid);
        }

        for (Hostile hostile : hostiles) {
            gameScreenView.addBullet(bullets, hostile.action(), hostile);
        }
    }

    /**
     * This method checks if the bullet hits asteroids on the screen.
     * @param bullet Bullet type
     * @param asteroid Asteroid type
     * @param newAsteroids ArrayList type
     * @param anchorPane AnchorPane type
     */
    private void checkBullet(Bullet bullet, Asteroid asteroid,
                             ArrayList<Asteroid> newAsteroids,
                             AnchorPane anchorPane) {
        if (bullet.isColliding(asteroid)) {
            bullet.setAlive(false);
            asteroid.setAlive(false);

            AudioController explosion = new AudioController();
            explosion.playExplosion();

            if (bullet.getOrigin() == player) {
                player.incrementScore(asteroid.getScore());
                gameScreenView.updateScore(player);
            }

            newAsteroids.addAll(asteroid.split());

            anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
        }

        if (!bullet.checkDistance()) {
            anchorPane.getChildren().remove(bullet.getView());
        }
    }

    /**
     * This method checks if the enemy got hit by the player bullets.
     * @param bullet Bullet type
     * @param hostile Hostile type
     * @param anchorPane AnchorPane type
     */
    private void checkEnemy(Bullet bullet, Hostile hostile, AnchorPane anchorPane) {
        if (bullet.isColliding(hostile) && bullet.getOrigin() == player) {
            bullet.setAlive(false);
            hostile.setAlive(false);
            player.incrementScore(hostile.getScore());
            gameScreenView.updateScore(player);
            anchorPane.getChildren().removeAll(bullet.getView(), hostile.getView());
        }

        if (!bullet.checkDistance()) {
            anchorPane.getChildren().remove(bullet.getView());
        }
    }

    /**
     * This method checks if the asteroids collide with the player,
     * making them loose a life.
     * @param asteroids List type
     * @param player Player type
     */
    private void checkAsteroidCollision(List<Asteroid> asteroids,
                                        Player player) {
        //check if player collided with an asteroid.
        for (SpaceEntity asteroid : asteroids) {
            if (player.isColliding(asteroid) && !isShieldActive) {
                updateLives(false, anchorPane);
            }
        }
    }

    /**
     * This method checks if the enemy bullets collide with the player,
     * making them loose a life.
     * @param bullets List type
     * @param player Player type
     * @param anchorPane AnchorPane type
     */
    private void checkBulletCollision(List<Bullet> bullets, Player player, AnchorPane anchorPane) {
        //check if player collided with an enemy bullet.
        for (Bullet bullet : bullets) {
            if (bullet.getOrigin() != player && player.isColliding(bullet) && !isShieldActive) {
                updateLives(false, anchorPane);
            }
            if (!bullet.checkDistance()) {
                anchorPane.getChildren().remove(bullet.getView());
            }
        }
    }

    /**
     * This method checks if the player is colliding with a hostile,
     * making them loose a life.
     * @param hostiles List type
     * @param player Player type
     */
    private void checkEnemyCollision(List<Hostile> hostiles,
                                     Player player) {
        //check if player collided with an enemy ship.
        for (SpaceEntity ufo : hostiles) {
            if (player.isColliding(ufo) && !isShieldActive) {
                updateLives(false, anchorPane);
            }
        }
    }

    /**
     * This method checks the player moves.
     * @param player Player type
     * @param asteroids List type
     * @param anchorPane AnchorPane type
     */
    private void checkPlayer(Player player, List<Asteroid> asteroids,
                             AnchorPane anchorPane) {
        player.move();
        player.cooldown();
        player.updateInvulnerabilityTime();

        //checks if player is qualified to get a life.
        if (player.getCurrentScore() >= scoreUp) {
            updateLives(true, anchorPane);
            player.setCurrentScore(player.getCurrentScore() - scoreUp);
        }

        if (isShieldActive) {
            player.getShield().move();
        }

        // checks if the shield time has expired
        if ((player.getInvulnerabilityTime() <= 0) && isShieldActive) {
            anchorPane.getChildren().remove(player.getShield().getView());
            isShieldActive = false;
        }

        //checks if player died.
        if (!player.hasLives()) {
            for (Asteroid asteroid : asteroids) {
                asteroids.remove(asteroid);
                anchorPane.getChildren().remove(asteroid.getView());
            }

            gameEnd(timer);
        }
    }

    /**
     * This method takes care of how the hostiles should spawn on the screen.
     */
    private void checkSpawnHostile() {
        if (Math.random() < hostileSpawnChance) {
            if (hostiles.size() == magicNumber) {

                for (ListIterator<Hostile> iterator = hostiles.listIterator();
                     iterator.hasNext();) {
                    Hostile ufo = iterator.next();
                    if (ufo instanceof Sniper) {
                        gameScreenView.addHostile(hostiles, new Juggernaut());
                    } else if (ufo instanceof Juggernaut) {
                        gameScreenView.addHostile(hostiles, new Sniper(getPlayer()));
                    }
                }
            }

            if (Math.round(Math.random()) == 0) {
                gameScreenView.addHostile(hostiles, new Sniper(getPlayer()));
            } else {
                gameScreenView.addHostile(hostiles, new Juggernaut());
            }

        }
    }

    /**
     * Method to call functions that execute behaviour of buttons.
     */
    public void checkButtons() throws IOException {
        if (up) {
            player.thrust();
            // Start thrust sound
            if (thrustSound.getClip() == null) {
                thrustSound.playSound("src/main/resources/audio/thrust.wav");
            } else if (thrustSound.getClip() != null && !thrustSound.getClip().isActive()) {
                thrustSound.playSound("src/main/resources/audio/thrust.wav");
            }
        } else if (thrustSound.getClip() != null && thrustSound.getClip().isActive()) {
            thrustSound.stop();
        }

        if (right) {
            player.rotateRight();
        }
        if (left) {
            player.rotateLeft();
        }

        if (left || right) {
            // Start rotate sound effect
            if (rotateSound.getClip() == null) {
                rotateSound.playSound("src/main/resources/audio/rotate.wav");
            } else if (rotateSound.getClip() != null && !rotateSound.getClip().isActive()) {
                rotateSound.playSound("src/main/resources/audio/rotate.wav");
            }

        } else if (rotateSound.getClip() != null && rotateSound.getClip().isActive()) {
            // Stop rotate sound effect
            rotateSound.stop();
        }

        if (space && player.canFire()) {
            gameScreenView.addBullet(bullets, player.shoot(), player);
        }
        if (fkey) {
            player.teleport();
        }
        if (down && player.getInvulnerabilityTime() > 0 && !isShieldActive) {
            gameScreenView.addShield(player, player.activateShield());
            isShieldActive = true;
        }

        checkPause(pkey);

        soundEffect = !soundEffect;
    }

    /**
     * Method that checks if the Pause Menu is showing on the screen or not.
     *
     * @throws IOException type
     */
    public void checkPause(boolean paused) throws IOException {
        if (paused) {
            isPaused = true;
            if (!gameScreenView.containsPauseScreen()) {
                gameScreenView.addPauseScreen();
            }
        } else {
            isPaused = false;
            gameScreenView.removePauseScreen();
        }
    }

    /**
     * Method triggered by the pause menu when clicking on the 'Continue' button.
     * This method will resume the game.
     */
    @FXML
    public void resumeGame() {
        robot.keyPress(KeyCode.P);
    }

    /**
     * Method triggered by the pause menu when clicking on the 'Quit' button.
     * This method will end the game and return to the MenuScreen.
     */
    @FXML
    public void quitGame() {
        robot.keyPress(KeyCode.Q);
    }

    /**
     * This method gets called when the player died.
     * Or when the game ends for whatever different reason.
     * Player is prompted to type in an alias.
     * The current game gets added to the database.
     * The highscore/leaderboard screen gets shown(??).
     */
    private void gameEnd(AnimationTimer timer) {
        isPaused = false;
        timer.stop();
        isStopped = true;
        gameScreenView.removePauseScreen();

        // creat end game screen
        gameScreenView.createEndGameScreen();
    }

    /**
     * Adds a new game to the database with the score and provided alias.
     *
     * @param alias String alias
     */
    public void saveScore(ActionEvent actionEvent, String alias) {
        if (!alias.isBlank() && !alias.isEmpty()) {
            Game game = new Game(0,
                    "username",
                    alias,
                    new Date(Calendar.getInstance().getTime().getTime()),
                    player.getTotalScore());

            Database d = new Database();
            d.insertGame(game);

            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(getLeaderBoardScreen());
        }
    }

    /**
     * Getter for the player.
     * @return player Player type
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}

