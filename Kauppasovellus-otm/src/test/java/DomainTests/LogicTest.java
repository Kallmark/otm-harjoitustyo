
package DomainTests;

import database.Database;
import database.UserDao;
import database.PurchaseDao;
import database.ProductDao;
import domain.User;
import domain.Logic;
import domain.Purchase;
import domain.Product;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class LogicTest {
    
    Database database;
    UserDao userDao;
    ProductDao productDao;
    PurchaseDao purchaseDao;
    Logic logic;
    

    public LogicTest() throws ClassNotFoundException {
        this.database = new Database("jdbc:sqlite:src/test/dbTest/testi.db");
        this.userDao = new UserDao(this.database);
        this.productDao = new ProductDao(database);
        this.purchaseDao = new PurchaseDao(database);
        this.logic = new Logic(userDao, productDao, purchaseDao);
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
        User kayttaja = new User(2, "Kalle", 10.0);
        logic.saveOrUpdateUser("2", "Kalle", "10.0");
        assertEquals(kayttaja.toString(), logic.findUser(2).toString());
    }
    
    @Test
    public void UpdateUserWorks() throws SQLException {
        User kayttaja = new User(2, "Kalle", 10.0);
        User kayttaja2 = new User(2, "Jaakko", 20.0);
        logic.saveOrUpdateUser("2", "Kalle", "10.0");
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        assertEquals(kayttaja2.toString(), logic.findUser(2).toString());
    }
    
    @Test
    public void findAllUsersWorks() throws SQLException{
        User kayttaja = new User(2, "Jaakko", 20.0);
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        assertEquals(2, logic.findAllUsers().size());
    }
    
    @Test
    public void findTopUsersWorks() throws SQLException{
        assertEquals(1, logic.findTopUsers(2000, 0).size());
    }
    
    @Test
    public void saveUsersWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        assertEquals(tuote.toString(), logic.findProduct(2).toString());
    }
    
    @Test 
    public void findOneProductWorks() throws SQLException {
        assertEquals("Pulla, id: 1, hinta: 1.0, määrä: 1, info: pullaa", logic.findProduct(1).toString());
    }
    
    @Test
    public void updateProductWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        Product tuote2 = new Product(2, "Tuote2", 20.0, 10, "Tuote");
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        logic.saveOrUpdateProduct("2", "Tuote2", "20.0", "10", "Tuote");
        assertEquals(tuote2.toString(), logic.findProduct(2).toString());
    }
    
    @Test
    public void findAllProductsWorks() throws SQLException {
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        assertEquals(2, productDao.findAll().size());
    }
    
    @Test
    public void savePurchaseWorks() {
        User kayttaja = new User(2, "Jaakko", 20.0);
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        Purchase ostos = new Purchase(kayttaja, tuote, 1000);
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        logic.saveOrUpdatePurchase(ostos);
        assertEquals("Käyttäjä: Jaakko, tuote: Tuote, aika: 1000", logic.findPurchase(2, 2).toString());
    }
    
    @Test 
    public void findOnePurcaseWorks() {
        assertEquals("Käyttäjä: Kalle, tuote: Pulla, aika: 1000", logic.findPurchase(1, 1).toString());
    }
    
    @Test
    public void purchaseAffectsBalance() {
        User kayttaja = new User(2, "Jaakko", 20.0);
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        Purchase ostos = new Purchase(kayttaja, tuote, 1000);
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        logic.saveOrUpdatePurchase(ostos);
        assertEquals(10.0, logic.findUser(2).getBalance(), 0.0);
    }
    
    @Test 
    public void purchaseAffectsNumberOfProduct() {
        User kayttaja = new User(2, "Jaakko", 20.0);
        Product tuote = new Product(2, "Tuote", 10.0, 10, "Tuote");
        logic.saveOrUpdateProduct("2", "Tuote", "10.0", "10", "Tuote");
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        Purchase ostos = new Purchase(kayttaja, tuote, 1000);
        logic.saveOrUpdatePurchase(ostos);
        assertEquals(9, logic.findProduct(2).getAmount());
    }
    
    @Test
    public void deleteUserWorks() {
        logic.deleteUser(1);
        assertEquals(0, logic.findAllUsers().size());
    }
    
    @Test
    public void deleteProductsWorks() {
        logic.deleteProduct(1);
        assertEquals(0, logic.findAllProducts().size());
    }
    
    @Test
    public void averageBalanceIsCorrect() {
        logic.saveOrUpdateUser("2", "Jaakko", "20.0");
        assertEquals(15.0, logic.calculateAverageBalance(), 0.0);
    }
    
    @Test
    public void overAllBalanceIsCorrect() {
        assertEquals(10.0, logic.calculateOverallBalance(), 0.0);
    }
    
    @Test
    public void findAllPurchasesWorks() {
        assertEquals(1, logic.findAllPurchases().size());
    }
    
    @Test
    public void findUserPurchaseHistoryWorks() {
        assertEquals(1, logic.findUsersPurchaseHistory(1).size());
    }
    @Test
    public void catchesErrorWhenSavingUser() {
        assertEquals(false, logic.saveOrUpdateUser("2", "Jaakko", "error"));
    }
    @Test
    public void catchesErrorWhenSavingProduct() {
        assertEquals(false, logic.saveOrUpdateProduct("2", "Tuote", "10.0", "error", "Tuote"));
    }
    

}
