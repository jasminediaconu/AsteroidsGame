import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import menu.LoginScreenController;
import menu.MainScreenController;
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

        FXMLLoader menuScreenLoader = new FXMLLoader(getClass()
                .getResource("/menu/fxml/menuScreen.fxml"));
        Parent menu = menuScreenLoader.load();
        Scene menuScene = new Scene(menu);

        // injecting login scene inside main scene
        MainScreenController mainScreenController = mainScreenLoader.getController();
        mainScreenController.setLoginScreen(loginScene);
        mainScreenController.setRegisterScreen(registerScene);


        LoginScreenController loginScreenController = loginScreenLoader.getController();
        loginScreenController.setMainScreen(mainScene);
        loginScreenController.setMenuScreen(menuScene);

        RegisterScreenController registerScreenController = registerScreenLoader.getController();
        registerScreenController.setMainScreen(mainScene);
        registerScreenController.setMenuScreen(menuScene);

        //scene.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("Asteroids");
        stage.setScene(mainScene);
        stage.show();

        stage.getIcons().add(new Image("menu/images/asteroid.png"));

    }
}
