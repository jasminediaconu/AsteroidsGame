package database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

class DatabaseTest {

    private static Database db;

    @BeforeEach
    void setUp() {
        db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
    }

    @Test
    void getUrl() {
        assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());
    }

    @Test
    void setUrl() {
        assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());

        db.setUrl("testurl");

        assertEquals("testurl", db.getUrl());
    }
}