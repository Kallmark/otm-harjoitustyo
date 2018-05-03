/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import database.Database;
import database.OstosDao;
import domain.Kayttaja;
import domain.Ostos;
import domain.Tuote;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kallmark
 */
public class OstosDaoTest {
    
    Database database;
    private OstosDao ostosdao;
    
    public OstosDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.ostosdao = new OstosDao(database);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        ostosdao.deleteAll();
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void deleteAllToimii() throws SQLException{
        Tuote tuote = new Tuote(1, "Tuote", 10.0, 10, "Tuote");
        Kayttaja kayttaja = new Kayttaja(1, "Kalle", 10.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 100);
        ostosdao.saveOrUpdate(ostos);
        ostosdao.deleteAll();
        assertEquals(0, ostosdao.findAll().size());
    }
    
    @Test
    public void saveJaEtsiToimii() throws SQLException{
        Tuote tuote = new Tuote(1, "Tuote", 10.0, 10, "Tuote");
        Kayttaja kayttaja = new Kayttaja(1, "Kalle", 10.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 100);
        ostosdao.saveOrUpdate(ostos);
        assertEquals(1, ostosdao.findAll().size());
    }
}
