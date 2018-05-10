/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseTests;

import database.Database;
import database.UserDao;
import database.ProductDao;
import domain.Product;
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
public class ProductDaoTest {

    Database database;
    ProductDao tuotedao;

    public ProductDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.tuotedao = new ProductDao(database);
    }

    @Before
    public void setUp() throws SQLException {
        this.database.init();
    }
    
    @Test
    public void deleteKaikkiToimii() throws SQLException{
        tuotedao.deleteAll();
        assertEquals(0, tuotedao.findAll().size());
    }
    
    @Test
    public void deleteOneWorks() throws SQLException{
        tuotedao.delete(1);
        assertEquals(null, tuotedao.findOne(1));
    }

    @Test
    public void saveJaEtsiToimii() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        assertEquals(tuote.toString(), tuotedao.findOne(2).toString());
    }
    
    @Test
    public void UpdateToimii() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        Product tuote2 = new Product(2, "Tuote2", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        tuotedao.saveOrUpdate(tuote2);
        
        assertEquals(tuote2.toString(), tuotedao.findOne(2).toString());
    }
    
    @Test
    
    public void findAllWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        tuotedao.saveOrUpdate(tuote);
        assertEquals(2, tuotedao.findAll().size());
    }

}
