import controllers.MainController;
import database.Database;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    /**
     * This function will start the MainScreen, which will have the option to
     * redirect the user to the Login or Register page.
     * @param stage The stage
     * @throws Exception When the FXML files couldn't be loaded.
     */

    @Override
    public void start(Stage stage) throws Exception {
        try {
            MainController mainController = new MainController();
            stage = mainController.getMainStage();

            //Loading scenes
            mainController.setRegisterScene();
            mainController.setLeaderBoardScene();
            mainController.setLoginScene();
            mainController.setMenuScene();

            // Switching scenes
            mainController.injectMainScreenScenes();
            mainController.injectLoginScreenScenes();
            mainController.injectRegisterScreenScenes();
            mainController.injectMenuScreenScenes();
            mainController.injectLeaderBoardScenes();

            Database.createDatabase();

            // Sets default icon of the application
            stage.getIcons().add(new Image("views/images/asteroid.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
