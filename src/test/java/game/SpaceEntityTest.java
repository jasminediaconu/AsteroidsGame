package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    void updateLocation_NotMovingTest() {
        // spaceEntity is not moving yet
        Point2D startLoc = spaceEntity.getLocation();
        double startRotation = spaceEntity.getRotation();

        spaceEntity.updateLocation();

        assertEquals(startLoc, spaceEntity.getLocation());
        assertEquals(startRotation, spaceEntity.getRotation());
    }

    @Test
    void updateLocation_MovingTest() {
        double startRot = 0;
        double rotSpeed = 10;

        spaceEntity.setRotation(startRot);
        spaceEntity.setRotationSpeed(rotSpeed);

        Point2D startLoc = new Point2D(0, 0);
        Point2D velocity = new Point2D(1, 1);

        spaceEntity.setLocation(startLoc);
        spaceEntity.setVelocity(velocity);

        spaceEntity.updateLocation();

        assertNotEquals(startLoc, spaceEntity.getLocation());
        assertNotEquals(startRot, spaceEntity.getRotation());

        assertEquals(startLoc.add(velocity), spaceEntity.getLocation());
        assertEquals(startRot + rotSpeed, spaceEntity.getRotation());
    }
}
