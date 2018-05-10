/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseTests;

import database.Database;
import database.PurchaseDao;
import domain.User;
import domain.Purchase;
import domain.Product;
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
public class PurchaseDaoTest {
    
    Database database;
    private PurchaseDao ostosdao;
    
    public PurchaseDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.ostosdao = new PurchaseDao(database);
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
    public void deleteAllWorks() throws SQLException{
        Product tuote = new Product(1, "Tuote", 10.0, 10, "Tuote");
        User kayttaja = new User(1, "Kalle", 10.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 100);
        ostosdao.saveOrUpdate(ostos);
        ostosdao.deleteAll();
        assertEquals(0, ostosdao.findAll().size());
    }
    
    @Test
    public void saveOrUpdateWorks() throws SQLException{
        Product tuote = new Product(1, "Tuote", 10.0, 10, "Tuote");
        User kayttaja = new User(1, "Kalle", 10.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 100);
        ostosdao.saveOrUpdate(ostos);
        assertEquals(1, ostosdao.findAll().size());
    }
}
