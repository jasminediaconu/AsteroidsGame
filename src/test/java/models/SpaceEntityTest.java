package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import models.game.Player;
import models.game.SpaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SpaceEntityTest {
    private transient ImageView view;
    private transient SpaceEntity spaceEntity;


    @BeforeEach
    void start() {
        spaceEntity = new Player();
    }

    @Test
    void constructorTest() {
        spaceEntity = new Player();
        assertNotNull(spaceEntity);
        assertEquals(spaceEntity.getView(), view);
    }

    @Test
    void getterTest() {
        assertEquals(0,spaceEntity.getRotation());

        Point2D centreScreen = new Point2D(400,400);
        assertEquals(centreScreen,spaceEntity.getLocation());

        assertEquals(0,spaceEntity.getRotationSpeed());

        Point2D speedVector = new Point2D(0,0);
        assertEquals(speedVector,spaceEntity.getVelocity());

        assertEquals("/views/sprites/playerShip.png",spaceEntity.getUrl());
    }

    @Test
    void setterTest() {
        spaceEntity.setRotation(50);
        assertEquals(50,spaceEntity.getRotation());

        Point2D point = new Point2D(450,284);
        spaceEntity.setLocation(point);
        assertEquals(point,spaceEntity.getLocation());

        spaceEntity.setRotationSpeed(50);
        assertEquals(50,spaceEntity.getRotationSpeed());

        Point2D speedVector = new Point2D(10,-40);
        spaceEntity.setVelocity(speedVector);
        assertEquals(speedVector,spaceEntity.getVelocity());
    }

    @Test
    void aliveOrDeadTest() {
        assertTrue(spaceEntity.isAlive());
        assertFalse(spaceEntity.isDead());

        spaceEntity.setAlive(false);
        assertTrue(spaceEntity.isDead());
        assertFalse(spaceEntity.isAlive());
    }
}
