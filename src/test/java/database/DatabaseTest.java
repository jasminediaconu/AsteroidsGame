package database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseTest {

    private static Database db;

    @BeforeEach
    void setUp() {
        db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
    }

    @Test
    void getUrl() {
        Assertions.assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());
    }

    @Test
    void setUrl() {
        Assertions.assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());

        db.setUrl("testurl");

        Assertions.assertEquals("testurl", db.getUrl());
    }
}