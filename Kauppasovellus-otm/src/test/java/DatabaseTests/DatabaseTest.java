package DatabaseTests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import database.Database;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DatabaseTest {

    public DatabaseTest() {
    }
    
    @Test
    public void tietokantaLoytyyTiedostosta() throws ClassNotFoundException {
        Database database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        assertNotNull(database);    
    }
    
    
    
}

