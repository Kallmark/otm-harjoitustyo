/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseTests;

import database.Database;
import database.KayttajaDao;
import domain.Kayttaja;
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

    public KayttajaDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.kayttajadao = new KayttajaDao(this.database);
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        
    }
    
    @Test
    public void saveJaEtsiToimii() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(1, "Kalle", 10.0);
        kayttajadao.saveOrUpdate(kayttaja);
        
        assertEquals(kayttaja.toString(), kayttajadao.findOne(1).toString());
    }
    
    @Test
    public void UpdateToimii() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(1, "Kalle", 10.0);
        Kayttaja kayttaja2 = new Kayttaja(1, "Jaakko", 20.0);
        kayttajadao.saveOrUpdate(kayttaja);
        kayttajadao.saveOrUpdate(kayttaja2);
        
        
        assertEquals(kayttaja2.toString(), kayttajadao.findOne(1).toString());
    }
    
    @Test
    public void etsiKaikkiToimii() throws SQLException{
        Kayttaja kayttaja = new Kayttaja(1, "Kalle", 10.0);
        assertEquals(1, kayttajadao.findAll().size());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
