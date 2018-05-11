
package DatabaseTests;

import database.Database;
import database.PurchaseDao;
import domain.User;
import domain.Purchase;
import domain.Product;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PurchaseDaoTest {
    
    Database database;
    private PurchaseDao purchaseDao;
    
    public PurchaseDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.purchaseDao = new PurchaseDao(database);
    }

    @Before
    public void setUp() throws SQLException {
        purchaseDao.deleteAll();
    }

    @Test
    public void deleteAllWorks() throws SQLException{
        Product tuote = new Product(1, "Tuote", 10.0, 10, "Tuote");
        User kayttaja = new User(1, "Kalle", 10.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 100);
        purchaseDao.save(ostos);
        purchaseDao.deleteAll();
        assertEquals(0, purchaseDao.findAll().size());
    }
    
    @Test
    public void saveOrUpdateWorks() throws SQLException{
        Product tuote = new Product(1, "Tuote", 10.0, 10, "Tuote");
        User kayttaja = new User(1, "Kalle", 10.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 100);
        purchaseDao.save(ostos);
        assertEquals(1, purchaseDao.findAll().size());
    }
}
