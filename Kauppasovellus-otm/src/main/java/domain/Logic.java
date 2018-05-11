package domain;

import database.UserDao;
import database.PurchaseDao;
import database.ProductDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Logic {

    private PurchaseDao ostosdao;
    private ProductDao tuotedao;
    private UserDao kayttajaDao;
    private Statistics stats;
    
    /**
     * Create Logic-object to handle the program's logic. 
     * @param kayttajaDao
     * @param tuotedao
     * @param ostosdao 
     */
    public Logic(UserDao kayttajaDao, ProductDao tuotedao, PurchaseDao ostosdao) {
        this.kayttajaDao = kayttajaDao;
        this.tuotedao = tuotedao;
        this.ostosdao = ostosdao;
        this.stats = new Statistics();
    }
    
    /**
     * Save a new user or update an existing user in the database. 
     * @param kayttaja
     * @return true if successfull and false id not succesfull
     */
    public boolean saveOrUpdateUser(User kayttaja) {

        try {
            this.kayttajaDao.saveOrUpdate(kayttaja);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Find all existing users in the database. 
     * @return a list of all users
     */
    public List<User> findAllUsers() {
        try {
            return this.kayttajaDao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find a specific user from the database. 
     * @param id primary key of the user 
     * @return 
     */
    public User findUser(Integer id) {
        try {
            return this.kayttajaDao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * Find users that have purchased products in a given time frame
     * @param secondsNow the current Unix time in seconds at the end of the time frame
     * @param secondsBefore the unix time in seconds at the start of the time frame
     * @return returns a LinkedMap with the users from top (most purchases) to bottom (only one purchase). 
     */
    public Map findTopUsers(long secondsNow, long secondsBefore) {
        try {
            return this.kayttajaDao.findAllTime(secondsNow, secondsBefore);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Delete a specific use from the database
     * @param id primary key for the user
     */
    public void deleteUser(Integer id) {
        try {
            this.kayttajaDao.delete(id);
        } catch (SQLException e) {
        }
    }
    /**
     * Save a new product or update an existinng one in the database
     * @param tuote
     * @return true if successfull, false if not successfull
     */
    public boolean saveOrUpdateProduct(Product tuote) {

        try {
            this.tuotedao.saveOrUpdate(tuote);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Find all existing products in the database
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        try {
            return this.tuotedao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * Find a specific product from the dataabse
     * @param id primary key for the product
     * @return 
     */
    public Product findProduct(Integer id) {
        try {
            return this.tuotedao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }
    /**
     * Delete a specific user from the database
     * @param id primary key for the product
     */
    public void deleteProduct(Integer id) {
        try {
            this.tuotedao.delete(id);
        } catch (Exception e) {
        }
    }
    
    /**
     * Find all the purchases that a specific user has made.
     * @param id primary key for the user.
     * @return a LinkedMap where the product is the key and purchase time is the value. 
     */
    public Map findUsersPurchaseHistory(Integer id) {
        try {
            return this.tuotedao.findUserHistory(id);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Save a new product or update an existing one in the database
     * @param ostos
     * @return true if successful and false if not successful
     */
    public boolean saveOrUpdatePurchase(Purchase ostos) {

        try {
            this.ostosdao.saveOrUpdate(ostos);
            Double newBalance = ostos.getUser().getBalance() - ostos.getProduct().getPrice();
            ostos.getUser().setBalance(newBalance);
            this.kayttajaDao.saveOrUpdate(ostos.getUser());
            int newAmount = ostos.getProduct().getAmount() - 1;
            ostos.getProduct().setAmount(newAmount);
            this.tuotedao.saveOrUpdate(ostos.getProduct());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    /**
     * Find all purchases found in the database
     * @return a list of all purchases.
     */
    public List<Purchase> findAllPurchases() {
        try {
            return this.ostosdao.findAll();
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find a specific purchase from the database
     * @param user_id primary key for the user
     * @param product_id primary key for the product
     * @return 
     */
    public Purchase findPurchase(Integer user_id, Integer product_id) {
        try {
            return this.ostosdao.findOne(user_id, product_id);
        } catch (SQLException e) {
            return null;
        }
    }
    
    /**
     * Calculates the overall balance from all of the users in the database. 
     * @return overall balance as a double. 
     */
    public Double calculateOverallBalance() {
        List<User> users = this.findAllUsers();
        return stats.calculateOverallBalance(users);
    }
    
    /**
     * Calculates the average balance from all of the users in the database.
     * @return the average balance as a double. 
     */
    public Double calculateAverageBalance() {
        List<User> users = this.findAllUsers();
        return stats.calculateAverageBalance(users);
    }

}
