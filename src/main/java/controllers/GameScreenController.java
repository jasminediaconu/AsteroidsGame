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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
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

/**
 * The type GameScreen ViewController.
 */
public class GameScreenController {

    private Scene menuScreen;

    public static final int screenSize = 800;

    public transient boolean isPaused = false;

    public transient boolean isStopped = false;

    public transient boolean soundEffect = false;

    public static int scoreUp = 10000;

    private transient ActionEvent event;

    //TODO: make spawn chances increase with a higher score.
    private static final double asteroidSpawnChance = 0.03;
    private static final double hostileSpawnChance = 0.0005;
    private static final int hostileCount = 0;

    private transient AnchorPane anchorPane;
    @FXML private transient Pane pauseScreen;

    private transient URL pauseScreenFile;
    private transient Scene gameScene;
    private transient List<Bullet> bullets = new ArrayList<>();
    private transient List<Asteroid> asteroids = new ArrayList<>();
    private transient List<Hostile> hostiles = new ArrayList<>();
    private transient List<SpaceEntity> ufos = new ArrayList<>();

    private transient Robot robot = new Robot();

    private transient Text score;

    //TODO: change text to icon
    private transient Text playerLives;
    private transient Player player;

    private transient boolean up = false;
    private transient boolean right = false;
    private transient boolean left = false;
    private transient boolean down = false;
    private transient boolean space = false;
    private transient boolean fkey = false;
    private transient boolean pkey = false;
    private transient boolean skey = false;

    private transient boolean isShieldActive = false;

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
                System.out.println("Invulnerability time: " + player.getInvulnerabilityTime());
                down = true;
            } else if ((e.getCode() == KeyCode.P)) {
                pkey = pkey ? false : true;
            } else if (e.getCode() == KeyCode.S) {
                skey = skey ? false : true;
            } else if (e.getCode() == KeyCode.Q) {
                gameEnd();
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
        AnimationTimer timer = new AnimationTimer() {
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
     * This method adds a Shield object when the user press the DOWN key.
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
     * @param asteroid SpaceEntity type
     */
    private void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
        addSpaceEntity(asteroid);
    }

    /**
     * This method adds a hostile UFO object on the screen.
     * @param hostile Hostile type
     */
    private void addHostile(Hostile hostile) {
        hostiles.add(hostile);
        addSpaceEntity(hostile);
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

    private void updateLives(boolean addLife) {
        if (addLife) {
            player.addLife();
        } else {
            player.removeLife();
            isShieldActive = true;
            addShield(player.activateShield());
        }
        playerLives.setText("Lives: " + player.getLives());
    }

    /**
     * This method updates the objects on the screen according to the Timer.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void onUpdate() {
        if (isStopped) {
            return;
        }

        //checkButtons();

        ArrayList<Medium> newMeds = new ArrayList<>();
        ArrayList<Small> newSmalls = new ArrayList<>();

        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.isColliding(asteroid)) {
                    bullet.setAlive(false);
                    asteroid.setAlive(false);

                    if (bullet.getOrigin() == player) {
                        player.incrementScore(asteroid.getScore());
                        score.setText("Score: " + player.getCurrentScore());
                    }

                    if (asteroid instanceof Large) {
                        Medium md1 = new Medium();
                        Medium md2 = new Medium();
                        md1.setLocation(asteroid.getLocation());
                        md2.setLocation(asteroid.getLocation());
                        newMeds.add(md1);
                        newMeds.add(md2);
                    } else if (asteroid instanceof Medium) {
                        Small sm1 = new Small();
                        Small sm2 = new Small();
                        sm1.setLocation(asteroid.getLocation());
                        sm2.setLocation(asteroid.getLocation());
                        newSmalls.add(sm1);
                        newSmalls.add(sm2);
                    }

                    anchorPane.getChildren().removeAll(bullet.getView(), asteroid.getView());
                }
                if (!bullet.checkDistance()) {
                    anchorPane.getChildren().remove(bullet.getView());
                }
            }
        }

        for (Small sm : newSmalls) {
            addAsteroid(sm);
        }
        for (Medium md : newMeds) {
            addAsteroid(md);
        }

        //check if player collided with an asteroid.
        for (SpaceEntity asteroid: asteroids) {
            if (player.isColliding(asteroid) && !isShieldActive) {
                updateLives(false);
            }
        }

        //check if player collided with an enemy bullet.
        for (Bullet bullet: bullets) {
            if (bullet.getOrigin() != player && player.isColliding(bullet) && !isShieldActive) {
                updateLives(false);
            }
            if (!bullet.checkDistance()) {
                anchorPane.getChildren().remove(bullet.getView());
            }
        }

        //check if player collided with an enemy ship.
        for (SpaceEntity ufo: ufos) {
            if (player.isColliding(ufo) && !isShieldActive) {
                updateLives(false);
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
            updateLives(true);
            player.setCurrentScore(player.getCurrentScore() - scoreUp);
        }

        if (isShieldActive) {
            player.getShield().move();
            System.out.println("The shield is moving with the player.");
        }

        // checks if the shield time has expired
        if ((player.getInvulnerabilityTime() <= 0) && isShieldActive) {
            anchorPane.getChildren().remove(player.getShield().getView());
            isShieldActive = false;
        }
        //checks if player died.
        if (!player.hasLives()) {
            gameEnd();
        }

        if (Math.random() < asteroidSpawnChance) {
            addAsteroid(Asteroid.spawnAsteroid());
        }

        if (Math.random() < hostileSpawnChance && hostileCount < 2) {
            addHostile(Hostile.spawnHostile());
        }
    }


    /**
     * Method to call functions that execute behaviour of buttons.
     */
    private void checkButtons() throws IOException {
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
        if (down && player.getInvulnerabilityTime() > 0 && !isShieldActive) {
            addShield(player.activateShield());
            isShieldActive = true;
        }
        if (pkey) {
            checkPause();
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
     * Method that checks if the Pause Menu is showing on the screen or not.
     * @throws IOException type
     */
    public void checkPause() throws IOException {
        if (!isPaused) {
            isPaused = true;
            pauseScreenFile = new File("src/main/resources/views/fxml/pauseScreen.fxml")
                    .toURI().toURL();
            pauseScreen =  FXMLLoader.load(pauseScreenFile);
            pauseScreen.setTranslateX(100.0);
            pauseScreen.setTranslateY(100.0);
            anchorPane.getChildren().add(pauseScreen);
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
     * Getter for Menu Screen scene.
     * @return menuScreen
     */
    public Scene getMenuScreen() {
        return menuScreen;
    }

    /**
     * Setter for Main Screen Scene.
     * @param scene type Scene
     */
    public void setMenuScreen(Scene scene) {
        menuScreen = scene;
    }

    /**
     * Method triggered by the pause menu when clicking on the 'Quit' button.
     * This method will end the game and return to the MenuScreen.
     */
    @FXML
    public void quitGame(ActionEvent actionEvent) {
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
        isPaused = true;
        isStopped = true;

        Text text = new Text("Fill in your alias");
        text.setLayoutX(251);
        text.setLayoutY(280);
        text.setStyle("-fx-font-size: 28px;");

        TextField aliasField = new TextField();
        aliasField.setPromptText("Alias");
        aliasField.setLayoutX(251);
        aliasField.setLayoutY(300);

        Button button = new Button("Save score");
        button.setLayoutX(251);
        button.setLayoutY(350);
        button.setOnAction((ActionEvent event) -> saveScore(aliasField.getText()));
        anchorPane.getChildren().addAll(text, aliasField, button);
    }


    /**
     * Adds a new game to the database with the score and provided alias.
     * @param alias String alias
     */
    private void saveScore(String alias) {
        if (!alias.isBlank() && !alias.isEmpty()) {

            Game game = new Game(0,
                    "username",
                    alias,
                    new Date(Calendar.getInstance().getTime().getTime()),
                    player.getTotalScore());

            Database d = new Database();
            d.insertGame(game);

            // TODO go to Leaderboard screen
            Platform.exit();
        }
    }
}
