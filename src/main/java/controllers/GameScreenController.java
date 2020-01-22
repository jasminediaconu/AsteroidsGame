package controllers;

import database.Database;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.robot.Robot;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.game.Asteroid;
import models.game.Bullet;
import models.game.Game;
import models.game.Hostile;
import models.game.Player;
import models.game.Shield;
import models.game.SpaceEntity;
import models.game.asteroids.Large;
import models.game.asteroids.Medium;
import models.game.asteroids.Small;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;

/**
 * The type GameScreen ViewController.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class GameScreenController {
    // default values
    public static int scoreUp = 10000;
    public static final int screenSize = 800;
    private static final  double asteroidSpawnChance = 0.01;
    private static final double hostileSpawnChance = 0.003;
    private static final int maxHostileCount = 2;
    private static final int magicNumber = 1;

    //FXML stuff
    @FXML
    private transient Pane pauseScreen;
    private transient Scene leaderBoardScreen;
    private transient URL pauseScreenFile;
    private transient Scene gameScene;
    private transient AnimationTimer timer;
    private transient AnchorPane anchorPane;

    // audio
    private transient AudioController rotateSound = new AudioController();
    private transient AudioController thrustSound = new AudioController();

    public transient boolean isPaused = false;
    public transient boolean isStopped = false;
    public transient boolean soundEffect = false;
    private static boolean pauseScreenInitiated = false;
    private transient boolean isShieldActive = false;


    // SpaceEntity lists
    private transient List<Bullet> bullets = new ArrayList<>();
    private transient List<Asteroid> asteroids = new ArrayList<>();
    private transient List<Hostile> hostiles = new ArrayList<>();

    //TODO: change text to icon
    private transient Text playerLives;
    private transient Text score;
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

    /**
     * GameScreenController constructor.
     */
    public GameScreenController() {

        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(screenSize, screenSize);
        gameScene = new Scene(createContent());

        checkKeyPressed(gameScene);
        checkKeyReleased(gameScene);
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
                gameEnd();
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
     * Sets up the initial scene of the game.
     *
     * @return The generated parent
     */
    private Parent createContent() {
        player = new Player();
        addSpaceEntity(player);

        player.getView().setScaleX(0.69);
        player.getView().setScaleY(0.69);

        // Attach the default style sheet to the Game Screen
        anchorPane.getStylesheets().add(new File("src/main/resources/defaultStyle.css")
                .toURI().toString());

        // Add labels to the screen
        score = new Text("Score: " + player.getCurrentScore());
        playerLives = new Text("Lives: " + player.getLives());

        playerLives.setStyle("-fx-font-size: 28px;");
        playerLives.setX(100.0);
        playerLives.setY(100.0);

        score.setStyle("-fx-font-size: 28px;");
        score.setX(400.0);
        score.setY(100.0);

        anchorPane.getChildren().add(score);
        anchorPane.getChildren().add(playerLives);
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
        return anchorPane;
    }

    private void createPauseScreen() {
        if (pauseScreen == null || pauseScreenFile == null) {
            try {
                pauseScreenFile = new File("src/main/resources/views/fxml/pauseScreen.fxml")
                        .toURI().toURL();
                pauseScreen = FXMLLoader.load(pauseScreenFile);
                pauseScreen.setTranslateX(100.0);
                pauseScreen.setTranslateY(100.0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method adds a Bullet object when the user press the SPACE key.
     *
     * @param bullet    Bullet type
     * @param firedFrom SpaceEntity that fired the bullet
     */
    public void addBullet(Bullet bullet, SpaceEntity firedFrom) {
        if (bullet == null) {
            return;
        }
        double x = firedFrom.getView().getTranslateX() + firedFrom.getView().getTranslateY() / 12;
        double y = firedFrom.getView().getTranslateY() + firedFrom.getView().getTranslateY() / 10;

        bullet.setLocation(new Point2D(x, y));

        bullets.add(bullet);
        addSpaceEntity(bullet);
    }

    /**
     * This method adds a Shield object when the user press the DOWN key.
     *
     * @param shield Shield type
     */
    private void addShield(Shield shield) {
        addSpaceEntity(shield);

        double x = player.getView().getTranslateX() + player.getView().getTranslateY();
        double y = player.getView().getTranslateY() + player.getView().getTranslateY();

        shield.setLocation(new Point2D(x, y));
    }

    /**
     * This method adds an Asteroid object on the screen.
     *
     * @param asteroid SpaceEntity type
     */
    private void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
        addSpaceEntity(asteroid);
    }

    /**
     * This method adds a hostile UFO object on the screen.
     *
     * @param hostile Hostile type
     */
    private void addHostile(Hostile hostile) {
        hostiles.add(hostile);
        addSpaceEntity(hostile);
    }

    /**
     * This method adds a generic SpaceEntity on the screen.
     *
     * @param object SpaceEntity type
     */
    private void addSpaceEntity(SpaceEntity object) {
        object.setView(new ImageView(new Image(object.getUrl())));
        object.getView().setTranslateX(object.getLocation().getX());
        object.getView().setTranslateY(object.getLocation().getY());
        anchorPane.getChildren().add(object.getView());
    }

    private void updateLives(boolean addLife) {
        if (addLife) {
            player.addLife();
        } else {
            player.removeLife();
            isShieldActive = true;
            addShield(player.activateShield());

            for (Asteroid asteroid : asteroids) {
                asteroid.setAlive(false);
                anchorPane.getChildren().remove(asteroid.getView());
            }
        }
        playerLives.setText("Lives: " + player.getLives());
    }

    /**
     * This method updates the objects on the screen according to the Timer.
     */
    private void onUpdate() {

        if (!pauseScreenInitiated) {
            createPauseScreen();
            pauseScreenInitiated = true;
        }

        if (isStopped) {
            return;
        }

        //checkButtons();

        generateElements(bullets, asteroids, hostiles, player);

        checkAsteroidCollision(asteroids, player);
        checkBulletCollision(bullets, player);
        checkEnemyCollision(hostiles, player);

        bullets.removeIf(SpaceEntity::isDead);
        asteroids.removeIf(SpaceEntity::isDead);
        hostiles.removeIf(SpaceEntity::isDead);

        bullets.forEach(SpaceEntity::move);
        asteroids.forEach(SpaceEntity::move);
        hostiles.forEach(Hostile::move);

        checkPlayer(player, asteroids);

        checkSpawnHostile();

        if (Math.random() < asteroidSpawnChance) {
            addAsteroid(Asteroid.spawnAsteroid(Math.random()));
        }

    }

    private void generateElements(List<Bullet> bullets, List<Asteroid> asteroids,
                                  List<Hostile> hostiles, Player player) {
        ArrayList<Asteroid> newAsteroids = new ArrayList<>();
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                checkBullet(bullet, asteroid, newAsteroids);
            }

            //check if a player bullet collided with an hostile
            for (Hostile hostile : hostiles) {
                checkEnemy(bullet, hostile);
            }
        }

        for (Asteroid asteroid : newAsteroids) {
            addAsteroid(asteroid);
        }

        for (Hostile hostile : hostiles) {
            addBullet(hostile.action(), hostile);
        }
    }

    private void checkBullet(Bullet bullet, Asteroid asteroid,
                             ArrayList<Asteroid> newAsteroids) {
        if (bullet.isColliding(asteroid)) {
            bullet.setAlive(false);
            asteroid.setAlive(false);

            AudioController explosion = new AudioController();
            Random random = new Random();
            int track = random.nextInt(4) + 1;
            explosion.playSound("src/main/resources/audio/exp_" + track + ".wav");

            if (bullet.getOrigin() == player) {
                player.incrementScore(asteroid.getScore());
                score.setText("Score: " + player.getCurrentScore());
            }

            newAsteroids.addAll(asteroid.split());

            anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
        }

        if (!bullet.checkDistance()) {
            anchorPane.getChildren().remove(bullet.getView());
        }
    }

    private void checkEnemy(Bullet bullet, Hostile hostile) {
        if (bullet.isColliding(hostile) && bullet.getOrigin() == player) {
            bullet.setAlive(false);
            hostile.setAlive(false);
            player.incrementScore(hostile.getScore());
            score.setText("Score: " + player.getCurrentScore());
            anchorPane.getChildren().removeAll(bullet.getView(), hostile.getView());
        }

        if (!bullet.checkDistance()) {
            anchorPane.getChildren().remove(bullet.getView());
        }
    }

    private void checkAsteroidCollision(List<Asteroid> asteroids,
                                        Player player) {
        //check if player collided with an asteroid.
        for (SpaceEntity asteroid : asteroids) {
            if (player.isColliding(asteroid) && !isShieldActive) {
                updateLives(false);
            }
        }
    }

    private void checkBulletCollision(List<Bullet> bullets, Player player) {

        //check if player collided with an enemy bullet.
        for (Bullet bullet : bullets) {
            if (bullet.getOrigin() != player && player.isColliding(bullet) && !isShieldActive) {
                updateLives(false);
            }
            if (!bullet.checkDistance()) {
                anchorPane.getChildren().remove(bullet.getView());
            }
        }
    }

    private void checkEnemyCollision(List<Hostile> hostiles,
                                     Player player) {
        //check if player collided with an enemy ship.
        for (SpaceEntity ufo : hostiles) {
            if (player.isColliding(ufo) && !isShieldActive) {
                updateLives(false);
            }
        }
    }

    private void checkPlayer(Player player, List<Asteroid> asteroids) {
        player.move();
        player.cooldown();
        player.updateInvulnerabilityTime();

        //checks if player is qualified to get a life.
        if (player.getCurrentScore() >= scoreUp) {
            updateLives(true);
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
            gameEnd();
        }
    }

    private void checkSpawnHostile() {
        if (Math.random() < hostileSpawnChance) {

            if (hostiles.size() >= maxHostileCount) {
                return;
            }

            if (hostiles.size() == magicNumber) {

                for (SpaceEntity ufo : hostiles) {
                    if (ufo instanceof Sniper) {
                        addHostile(new Juggernaut());
                    }
                    if (ufo instanceof Juggernaut) {
                        addHostile(new Sniper(getPlayer()));
                    }
                }

            }

            if (Math.round(Math.random()) == 0) {
                addHostile(new Sniper(getPlayer()));
            } else {
                addHostile(new Juggernaut());
            }
        }
    }

    /**
     * Method to call functions that execute behaviour of buttons.
     */
    private void checkButtons() throws IOException {
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
            addBullet(player.shoot(), player);
        }
        if (fkey) {
            player.teleport();
        }
        if (down && player.getInvulnerabilityTime() > 0 && !isShieldActive) {
            addShield(player.activateShield());
            isShieldActive = true;
        }

        checkPause(pkey);

        if (skey) {
            //TODO turn on sound
            //TODO turn off sound
            soundEffect = !soundEffect;
        }
    }

    /**
     * Method that checks if the Pause Menu is showing on the screen or not.
     *
     * @throws IOException type
     */
    public void checkPause(boolean paused) throws IOException {
        if (paused) {
            isPaused = true;
            if (!anchorPane.getChildren().contains(pauseScreen)) {
                anchorPane.getChildren().add(pauseScreen);
            }
        } else {
            isPaused = false;
            anchorPane.getChildren().remove(pauseScreen);
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
    private void gameEnd() {
        System.out.println("Game end");
        isPaused = false;
        timer.stop();
        isStopped = true;
        anchorPane.getChildren().remove(pauseScreen);

        Pane saveScreen = new Pane();
        saveScreen.setPrefSize(400, 270);
        saveScreen.setStyle("-fx-background-color: black");
        saveScreen.setTranslateX(200.0);
        saveScreen.setTranslateY(200.0);

        Text text = new Text("Fill in your alias");
        text.setLayoutX(90);
        text.setLayoutY(60);
        text.setStyle("-fx-font-size: 28px;");

        TextField aliasField = new TextField();
        aliasField.setPromptText("Alias");
        aliasField.setLayoutX(90);
        aliasField.setLayoutY(110);

        Button button = new Button("Save score");
        button.setLayoutX(90);
        button.setLayoutY(180);

        saveScreen.getChildren().addAll(text, aliasField, button);

        button.setOnAction((ActionEvent event) -> saveScore(event, aliasField.getText()));
        anchorPane.getChildren().add(saveScreen);
        saveScreen.toFront();
    }

    /**
     * Adds a new game to the database with the score and provided alias.
     *
     * @param alias String alias
     */
    private void saveScore(ActionEvent actionEvent, String alias) {
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

