package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import models.game.Player;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;
import org.junit.jupiter.api.Test;


public class HostileTest {

    @Test
    void ufoLinkTest() {
        Juggernaut jg = new Juggernaut();
        assertEquals("/views/sprites/Juggernaut.png", jg.getUrl());
        Sniper sp = new Sniper(new Player());
        assertEquals("/views/sprites/Sniper.png", sp.getUrl());
    }

}
