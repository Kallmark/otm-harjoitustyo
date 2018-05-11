
package DatabaseTests;

import database.Database;
import database.UserDao;
import database.PurchaseDao;
import database.ProductDao;
import domain.User;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserDaoTest {

    Database database;
    UserDao userDao;
    ProductDao productDao;
    PurchaseDao purchaseDao;

    public UserDaoTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.userDao = new UserDao(this.database);
        this.productDao = new ProductDao(database);
        this.purchaseDao = new PurchaseDao(database);
    }

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        this.database.init();
    }
    
    @Test 
    public void testDatabaseIsCorrect() throws SQLException {
        assertEquals("Kalle, id: 1, saldo: 10.0", userDao.findOne(1).toString());
    }
    
    @Test
    public void deleteAllWorks() throws SQLException{
        userDao.deleteAll();
        assertEquals(0, userDao.findAll().size());
    }
    
    @Test
    
    public void deleteOneWorks() throws SQLException {
        userDao.delete(1);
        assertEquals(0, userDao.findAll().size());
    }
    
    
    @Test
    public void saveWorks() throws SQLException {
        User kayttaja = new User(2, "Kalle", 10.0);
        userDao.save(kayttaja);
        assertEquals(kayttaja.toString(), userDao.findOne(2).toString());
    }
    
    @Test
    public void updateWorks() throws SQLException {
        User kayttaja = new User(2, "Kalle", 10.0);
        User kayttaja2 = new User(2, "Jaakko", 20.0);
        userDao.save(kayttaja);
        userDao.save(kayttaja2);
        assertEquals(kayttaja2.toString(), userDao.findOne(2).toString());
    }
    
    @Test
    public void findAllWorks() throws SQLException{
        User kayttaja = new User(2, "Jaakko", 20.0);
        userDao.save(kayttaja);
        assertEquals(2, userDao.findAll().size());
    }
    
    @Test
    public void findTopUsersWorks() throws SQLException{
        assertEquals(1, userDao.findAllTime(2000, 0).size());
    }

}
