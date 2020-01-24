package views;

import controllers.GameScreenController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import models.game.Asteroid;
import models.game.Bullet;
import models.game.Hostile;
import models.game.Player;
import models.game.Shield;
import models.game.SpaceEntity;

public class GameScreenView {

    public static final int screenSize = 800;
    public transient GameScreenController gameScreenController;
    private transient AnchorPane anchorPane;

    private transient Text playerLives;
    private transient Text score;

    @FXML private transient Pane pauseScreen;
    private transient URL pauseScreenFile;

    /**
     * The GameScreenView constructor.
     *
     * @param gameScreenController GameScreenController type
     */
    public GameScreenView(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(screenSize, screenSize);
    }

    /**
     * Sets up the initial scene of the game.
     * @param player Player type
     * @return The generated parent
     */
    public AnchorPane createContent(Player player) {

        addSpaceEntity(player);
        player.getView().setScaleX(0.69);
        player.getView().setScaleY(0.69);

        // Attach the default style sheet to the Game Screen
        anchorPane.getStylesheets().add(new File("src/main/resources/defaultStyle.css")
                .toURI().toString());

        score = new Text("Score: " + player.getTotalScore());
        playerLives = new Text("Lives: " + player.getLives());

        playerLives.setStyle("-fx-font-size: 28px;");
        playerLives.setX(100.0);
        playerLives.setY(100.0);

        score.setStyle("-fx-font-size: 28px;");
        score.setX(400.0);
        score.setY(100.0);

        anchorPane.getChildren().add(score);
        anchorPane.getChildren().add(playerLives);

        return anchorPane;
    }

    /**
     * This method initializes the GameScreen.
     */
    public void createEndGameScreen() {
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

        button.setOnAction((ActionEvent event) -> gameScreenController.saveScore(event,
                aliasField.getText()));
        anchorPane.getChildren().add(saveScreen);
        saveScreen.toFront();
    }

    /**
     * This method adds a generic SpaceEntity on the screen.
     *
     * @param object SpaceEntity type
     */
    public void addSpaceEntity(SpaceEntity object) {
        object.setView(new ImageView(new Image(object.getUrl())));
        object.getView().setTranslateX(object.getLocation().getX());
        object.getView().setTranslateY(object.getLocation().getY());
        anchorPane.getChildren().add(object.getView());
    }

    /**
     * This method adds a Bullet object when the user
     * press the SPACE key.
     *
     * @param bullet    Bullet type
     * @param firedFrom SpaceEntity that fired the bullet
     */
    public void addBullet(List<Bullet> bullets, Bullet bullet,
                          SpaceEntity firedFrom) {
        if (bullet != null) {
            double x = firedFrom.getView().getTranslateX()
                    + firedFrom.getView().getTranslateY() / 12;
            double y = firedFrom.getView().getTranslateY()
                    + firedFrom.getView().getTranslateY() / 10;

            bullet.setLocation(new Point2D(x, y));

            bullets.add(bullet);
            addSpaceEntity(bullet);
        }
    }

    /**
     * This method adds a Shield object when the user
     * press the DOWN key.
     *
     * @param shield Shield type
     */
    public void addShield(Player player, Shield shield) {
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
    public void addAsteroid(List<Asteroid> asteroids, Asteroid asteroid) {
        asteroids.add(asteroid);
        addSpaceEntity(asteroid);
    }

    /**
     * This method adds a hostile UFO object on the screen.
     *
     * @param hostile Hostile type
     */
    public void addHostile(List<Hostile> hostiles, Hostile hostile) {
        hostiles.add(hostile);
        addSpaceEntity(hostile);
    }

    /**
     * This method updates the score on the screen.
     * @param player Player type
     */
    public void updateScore(Player player) {
        score.setText("Score: " + player.getCurrentScore());
    }

    /**
     * This method updates the lives of the player on the screen.
     * @param player Player type
     */
    public void setLives(Player player) {
        playerLives.setText("Lives: " + player.getLives());
    }

    /**
     * This method it is called when the Game starts and the PauseScreen gets initialized.
     */
    public void createPauseScreen() {
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
     * This method removes the PauseScreen from the screen.
     */
    public void removePauseScreen() {
        anchorPane.getChildren().remove(pauseScreen);
    }

    /**
     * This method adds the PauseScreen from the screen.
     */
    public void addPauseScreen() {
        anchorPane.getChildren().add(pauseScreen);
    }

    /**
     * This method checks if the PauseScreen is on the screen.
     */
    public boolean containsPauseScreen() {
        return anchorPane.getChildren().contains(pauseScreen);
    }
}
