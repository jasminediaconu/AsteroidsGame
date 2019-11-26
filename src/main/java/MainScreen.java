import database.Database;
import game.GameScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menu.LeaderBoardScreenController;
import menu.LoginScreenController;
import menu.MainScreenController;
import menu.MenuScreenController;
import menu.RegisterScreenController;

public class MainScreen extends Application {

    /**
     * This function will start the MainScreen, which will have the option to
     * redirect the user to the Login or Register page.
     * @param stage The stage
     * @throws Exception When the FXML files couldn't be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {

        //Database db = new Database();
        Database.createDatabase();
        FXMLLoader mainScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/mainScreen.fxml"));
        Parent main = mainScreenLoader.load();
        Scene mainScene = new Scene(main);

        // getting loader and a pane for the login scene
        FXMLLoader loginScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/login.fxml"));
        Parent login = loginScreenLoader.load();
        Scene loginScene = new Scene(login);

        // getting loader and a pane for the register scene
        FXMLLoader registerScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/register.fxml"));
        Parent register = registerScreenLoader.load();
        Scene registerScene = new Scene(register);

        // injecting login scene inside main scene
        MainScreenController mainScreenController = mainScreenLoader.getController();
        mainScreenController.setLoginScreen(loginScene);
        mainScreenController.setRegisterScreen(registerScene);

        FXMLLoader menuScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/menuScreen.fxml"));
        Parent menu = menuScreenLoader.load();
        Scene menuScene = new Scene(menu);

        LoginScreenController loginScreenController = loginScreenLoader.getController();
        loginScreenController.setMainScreen(mainScene);
        loginScreenController.setMenuScreen(menuScene);

        RegisterScreenController registerScreenController = registerScreenLoader.getController();
        registerScreenController.setMainScreen(mainScene);
        registerScreenController.setMenuScreen(menuScene);

        FXMLLoader leaderBoardScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/leaderBoardScreen.fxml"));
        Parent leaderBoard = leaderBoardScreenLoader.load();
        Scene leaderBoardScene = new Scene(leaderBoard);

        FXMLLoader gameScreenLoader = new FXMLLoader(getClass()
                .getResource("/game/fxml/gameScreen.fxml"));
        Parent game = gameScreenLoader.load();
        Scene gameScene = new Scene(game);

        MenuScreenController menuScreenController = menuScreenLoader.getController();
        menuScreenController.setLeaderBoardScreen(leaderBoardScene);
        menuScreenController.setGameScreen(gameScene);

        LeaderBoardScreenController leaderBoardScreenController
                = leaderBoardScreenLoader.getController();
        leaderBoardScreenController.setMenuScreen(menuScene);


        //scene.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);


        // Changes the cursor type of the application
        //mainScene.setCursor(Cursor.CROSSHAIR);

        stage.setTitle("Asteroids");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();

        // Sets default icon of the application
        stage.getIcons().add(new Image("menu/images/asteroid.png"));
    }

}
