/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseTests;

import database.Database;
import database.KayttajaDao;
import database.TuoteDao;
import domain.Tuote;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
public class TuoteDaoTest {

    Database database;
    TuoteDao tuotedao;

    public TuoteDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.tuotedao = new TuoteDao(database);
    }

    @Before
    public void setUp() throws SQLException {
        tuotedao.deleteAll();
    }
    
    @Test
    public void deleteKaikkiToimii() throws SQLException{
        Tuote tuote = new Tuote(1, "Tuote", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        tuotedao.deleteAll();
        assertEquals(0, tuotedao.findAll().size());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void saveJaEtsiToimii() throws SQLException {
        Tuote tuote = new Tuote(1, "Tuote", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        assertEquals(tuote.toString(), tuotedao.findOne(1).toString());
    }
    
    @Test
    public void UpdateToimii() throws SQLException {
        Tuote tuote = new Tuote(1, "Tuote", 10.0, 10, "Tuote");
        Tuote tuote2 = new Tuote(1, "Tuote2", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        tuotedao.saveOrUpdate(tuote2);
        
        assertEquals(tuote2.toString(), tuotedao.findOne(1).toString());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
