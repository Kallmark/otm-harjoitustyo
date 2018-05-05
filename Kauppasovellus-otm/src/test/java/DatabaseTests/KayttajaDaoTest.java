/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseTests;

import database.Database;
import database.KayttajaDao;
import database.OstosDao;
import database.TuoteDao;
import domain.Kayttaja;
import domain.Ostos;
import domain.Tuote;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kallmark
 */
public class KayttajaDaoTest {

    Database database;
    KayttajaDao kayttajadao;
    TuoteDao tuotedao;
    OstosDao ostosdao;

    public KayttajaDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.kayttajadao = new KayttajaDao(this.database);
        this.tuotedao = new TuoteDao(database);
        this.ostosdao = new OstosDao(database);
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        this.database.init();
    }
    
    @Test 
    
    public void testDatabaseIsCorrect() throws SQLException {
        assertEquals("Kalle, id: 1, saldo: 10.0", kayttajadao.findOne(1).toString());
    }
    
    @Test
    public void deleteKaikkiToimii() throws SQLException{
        kayttajadao.deleteAll();
        assertEquals(0, kayttajadao.findAll().size());
    }
    
    //@Test
    
    //public void deleteOneWorks() throws SQLException {
    //    kayttajadao.delete(1);
    //    assertEquals(0, kayttajadao.findAll().size());
    //}
    
    
    @Test
    public void saveJaEtsiToimii() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(2, "Kalle", 10.0);
        kayttajadao.saveOrUpdate(kayttaja);
        assertEquals(kayttaja.toString(), kayttajadao.findOne(2).toString());
    }
    
    @Test
    public void UpdateToimii() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(2, "Kalle", 10.0);
        Kayttaja kayttaja2 = new Kayttaja(2, "Jaakko", 20.0);
        kayttajadao.saveOrUpdate(kayttaja);
        kayttajadao.saveOrUpdate(kayttaja2);
        assertEquals(kayttaja2.toString(), kayttajadao.findOne(2).toString());
    }
    
    @Test
    public void etsiKaikkiToimii() throws SQLException{
        Kayttaja kayttaja = new Kayttaja(2, "Jaakko", 20.0);
        kayttajadao.saveOrUpdate(kayttaja);
        assertEquals(2, kayttajadao.findAll().size());
    }
    
    @Test
    public void findAllTimeToimii() throws SQLException{
        assertEquals(1, kayttajadao.findAllTime(2000, 0).size());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
