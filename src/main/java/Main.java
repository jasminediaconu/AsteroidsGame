import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class Main extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(800);
        settings.setTitle("Asteroids Game");
    }

    public static void main(String[] args) {
        launch(args);
    }
}