/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import database.Database;
import database.KayttajaDao;
import database.OstosDao;
import database.TuoteDao;
import domain.Kayttaja;
import domain.Logic;
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
public class LogicTest {
    
    Database database;
    KayttajaDao kayttajadao;
    TuoteDao tuotedao;
    OstosDao ostosdao;
    Logic logic;
    

    public LogicTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.kayttajadao = new KayttajaDao(this.database);
        this.tuotedao = new TuoteDao(database);
        this.ostosdao = new OstosDao(database);
        this.logic = new Logic(kayttajadao, tuotedao, ostosdao);
    }
    
    
    @Before
    public void setUp() {
        this.database.init();
    }
    
    
    @Test 
    
    public void findOneUserWorks() throws SQLException {
        assertEquals("Kalle, id: 1, saldo: 10.0", logic.findUser(1).toString());
    }
    
    @Test
    public void saveUserWorks() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(2, "Kalle", 10.0);
        logic.saveOrUpdateUser(kayttaja);
        assertEquals(kayttaja.toString(), logic.findUser(2).toString());
    }
    
    @Test
    public void UpdateUserWorks() throws SQLException {
        Kayttaja kayttaja = new Kayttaja(2, "Kalle", 10.0);
        Kayttaja kayttaja2 = new Kayttaja(2, "Jaakko", 20.0);
        logic.saveOrUpdateUser(kayttaja);
        logic.saveOrUpdateUser(kayttaja2);
        assertEquals(kayttaja2.toString(), logic.findUser(2).toString());
    }
    
    @Test
    public void findAllUsersWorks() throws SQLException{
        Kayttaja kayttaja = new Kayttaja(2, "Jaakko", 20.0);
        logic.saveOrUpdateUser(kayttaja);
        assertEquals(2, logic.findAllUsers().size());
    }
    
    @Test
    public void findTopUsersWorks() throws SQLException{
        assertEquals(1, logic.findTopUsers(2000, 0).size());
    }
    
    @Test
    public void saveUsersWorks() throws SQLException {
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct(tuote);
        assertEquals(tuote.toString(), logic.findProduct(2).toString());
    }
    
    @Test 
    public void findOneProductWorks() throws SQLException {
        assertEquals("Pulla, id: 1, hinta: 1.0, määrä: 1, info: pullaa", logic.findProduct(1).toString());
    }
    
    @Test
    public void updateProductWorks() throws SQLException {
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        Tuote tuote2 = new Tuote(2, "Tuote2", 20.0, 10, "Tuote");
        logic.saveOrUpdateProduct(tuote);
        logic.saveOrUpdateProduct(tuote2);
        
        assertEquals(tuote2.toString(), logic.findProduct(2).toString());
    }
    
    @Test
    public void findAllProductsWorks() throws SQLException {
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct(tuote);
        assertEquals(2, tuotedao.findAll().size());
    }
    
    @Test
    public void savePurchaseWorks() {
        Kayttaja kayttaja = new Kayttaja(2, "Jaakko", 20.0);
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        Ostos ostos = new Ostos(kayttaja, tuote, 1000);
        logic.saveOrUpdateProduct(tuote);
        logic.saveOrUpdateUser(kayttaja);
        logic.saveOrUpdatePurchase(ostos);
        assertEquals("Käyttäjä: Jaakko, tuote: Tuote, aika: 1000", logic.findPurchase(2, 2).toString());
    }
    
    @Test 
    public void findOnePurcaseWorks() {
        assertEquals("Käyttäjä: Kalle, tuote: Pulla, aika: 1000", logic.findPurchase(1, 1).toString());
    }
    
    @Test
    public void purchaseAffectsBalance() {
        Kayttaja kayttaja = new Kayttaja(2, "Jaakko", 20.0);
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        Ostos ostos = new Ostos(kayttaja, tuote, 1000);
        logic.saveOrUpdateProduct(tuote);
        logic.saveOrUpdateUser(kayttaja);
        logic.saveOrUpdatePurchase(ostos);
        assertEquals(10.0, logic.findUser(2).getSaldo(), 0.0);
    }
    
    @Test 
    public void purchaseAffectsNumberOfProduct() {
        Kayttaja kayttaja = new Kayttaja(2, "Jaakko", 20.0);
        Tuote tuote = new Tuote(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct(tuote);
        logic.saveOrUpdateUser(kayttaja);
        Ostos ostos = new Ostos(kayttaja, tuote, 1000);
        logic.saveOrUpdatePurchase(ostos);
        assertEquals(9, logic.findProduct(2).getMaara());
    }
    

}
