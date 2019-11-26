package menu;

/**
 * The type Controller.
 */
public abstract class Controller {

    protected MainScreenController mainScreenController;

    public final MainScreenController getMainScreenController() {
        return mainScreenController;
    }

    public final void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    /**
     * When the screen is changed this function will be called to update the screen.
     */
    public abstract void update();

    public void init() {
    }
}
