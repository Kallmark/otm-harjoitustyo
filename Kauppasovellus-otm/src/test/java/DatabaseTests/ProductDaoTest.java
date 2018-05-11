package DatabaseTests;

import database.Database;
import database.ProductDao;
import domain.Product;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductDaoTest {

    Database database;
    ProductDao productDao;

    public ProductDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.productDao = new ProductDao(database);
    }

    @Before
    public void setUp() throws SQLException {
        this.database.init();
    }

    @Test
    public void deleteAllWorks() throws SQLException {
        productDao.deleteAll();
        assertEquals(0, productDao.findAll().size());
    }

    @Test
    public void deleteOneWorks() throws SQLException {
        productDao.delete(1);
        assertEquals(null, productDao.findOne(1));
    }

    @Test
    public void saveWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        productDao.saveOrUpdate(tuote);
        assertEquals(tuote.toString(), productDao.findOne(2).toString());
    }

    @Test
    public void UpdateWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        Product tuote2 = new Product(2, "Tuote2", 10.0, 10, "Tuote");
        productDao.saveOrUpdate(tuote);
        productDao.saveOrUpdate(tuote2);
        assertEquals(tuote2.toString(), productDao.findOne(2).toString());
    }

    @Test
    public void findAllWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        productDao.saveOrUpdate(tuote);
        assertEquals(2, productDao.findAll().size());
    }

}
