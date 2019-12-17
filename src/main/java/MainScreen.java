import com.sun.javafx.application.PlatformImpl;
import com.sun.javafx.css.StyleManager;
import database.Database;
import game.SpaceEntity;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import menu.ViewController;


public class MainScreen extends Application {

    /**
     * This function will start the MainScreen, which will have the option to
     * redirect the user to the Login or Register page.
     * @param stage The stage
     * @throws Exception When the FXML files couldn't be loaded.
     */

    @Override
    public void start(Stage stage) throws Exception {
        try {
            ViewController viewController = new ViewController();
            stage = viewController.getMainStage();

            //Loading scenes
            viewController.setGameScene();
            viewController.setRegisterScene();
            viewController.setLeaderBoardScene();
            viewController.setLoginScene();
            viewController.setMenuScene();

            // Switching scenes
            viewController.injectMainScreenScenes();
            viewController.injectLoginScreenScenes();
            viewController.injectRegisterScreenScenes();
            viewController.injectMenuScreenScenes();
            viewController.injectLeaderBoardScenes();
            viewController.injectGameScreenScenes();

            //Database db = new Database();
            Database.createDatabase();

            //scene.setFill(Color.TRANSPARENT);
            //stage.initStyle(StageStyle.TRANSPARENT);

            // Changes the cursor type of the application
            //mainScene.setCursor(Cursor.CROSSHAIR);

            // Sets default icon of the application
            stage.getIcons().add(new Image("menu/images/asteroid.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
