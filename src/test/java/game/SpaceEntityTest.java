package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceEntityTest {
    Image image;
    ImageView view;
    SpaceEntity spaceEntity;


    @BeforeEach
    public void start() throws Exception {
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
        spaceEntity.getRotation();
        assertEquals(0,spaceEntity.getRotation());

        spaceEntity.getLocation();
        Point2D centreScreen = new Point2D(400,400);
        assertEquals(centreScreen,spaceEntity.getLocation());

        assertEquals(0,spaceEntity.getRotationSpeed());

        Point2D speedVector = new Point2D(0,0);
        assertEquals(speedVector,spaceEntity.getVelocity());

        assertEquals("/game/sprites/playerShip.png",spaceEntity.getUrl());
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

    @Test
    void moveTest() {
        spaceEntity.setVelocity(new Point2D(1,1));
        spaceEntity.setRotationSpeed(2);

        Point2D center = new Point2D(400,400);
        assertEquals(center,spaceEntity.getLocation());

        spaceEntity.move();

        Point2D newloc = new Point2D(401,401);
        assertEquals(newloc,spaceEntity.getLocation());
        assertEquals(2,spaceEntity.getRotation());
    }
}
