package game;

import game.hostiles.LargeUfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LargeUfoTest {

    @Test
    public void getUrlTest() {
        LargeUfo lf = new LargeUfo();
        Assertions.assertEquals("/game/sprites/enemyBlue1.png", lf.getUrl());
    }
}
