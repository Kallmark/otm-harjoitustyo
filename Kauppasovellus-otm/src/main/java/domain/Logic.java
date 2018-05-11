package domain;

import database.UserDao;
import database.PurchaseDao;
import database.ProductDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Logic {

    private PurchaseDao purchaseDao;
    private ProductDao productDao;
    private UserDao userDao;
    private Statistics stats;

    /**
     * Create Logic-object to handle the program's logic.
     *
     * @param userDao
     * @param tuotedao
     * @param purchaseDao
     */
    public Logic(UserDao userDao, ProductDao productDao, PurchaseDao purchaseDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.purchaseDao = purchaseDao;
        this.stats = new Statistics();
    }

    /**
     * Save a new user or update an existing user in the database.
     *
     * @param id user's primary key as a string
     * @param name user's name as a string
     * @param balance user's balance as a string
     * @return true if successful and false id not successful.
     */
    public boolean saveUser(String id, String name, String balance) {
        try {
            User user = new User(Integer.parseInt(id), name, Double.parseDouble(balance));
            this.userDao.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Update an existing user in the database.
     *
     * @param id user's primary key as a string
     * @param name user's name as a string
     * @param balance user's balance as a string
     * @return true if successful and false id not successful.
     */
    public boolean updateUser(String id, String name, String balance) {
        try {
            User user = new User(Integer.parseInt(id), name, Double.parseDouble(balance));
            this.userDao.update(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Find all existing users in the database.
     *
     * @return List<User> of all users
     */
    public List<User> findAllUsers() {
        try {
            return this.userDao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find a specific user from the database.
     *
     * @param id primary key of the user
     * @return wanted User or null if not found.
     */
    public User findUser(Integer id) {
        try {
            return this.userDao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find users that have purchased products in a given time frame
     *
     * @param secondsNow the current Unix time in seconds at the end of the time
     * frame
     * @param secondsBefore the Unix time in seconds at the start of the time
     * frame
     * @return returns a LinkedMap<User, Integer> where User is key and value is
     * amount of purchases. LinkedMap gives the users from top (most purchases)
     * to bottom (only one purchase).
     */
    public Map findTopUsers(long secondsNow, long secondsBefore) {
        try {
            return this.userDao.findAllTime(secondsNow, secondsBefore);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Delete a specific user from the database
     *
     * @param id primary key for the user
     */
    public void deleteUser(Integer id) {
        try {
            this.userDao.delete(id);
        } catch (SQLException e) {
        }
    }

    /**
     * Save a new product to the database.
     *
     * @param id primary key of the product as a string.
     * @param name name of the product as a string.
     * @param price price of the product as a string.
     * @param amount amount of the product in stock as a string.
     * @param info info about the product as a string.
     * @return true if successful and false if not successful.
     */
    public boolean saveProduct(String id, String name, String price, String amount, String info) {
        try {
            Product product = new Product(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(amount), info);
            this.productDao.save(product);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Update and existing product in the database.
     *
     * @param id primary key of the product as a string.
     * @param name name of the product as a string.
     * @param price price of the product as a string.
     * @param amount amount of the product in stock as a string.
     * @param info info about the product as a string.
     * @return true if successful and false if not successful.
     * @return
     */
    public boolean updateProduct(String id, String name, String price, String amount, String info) {
        try {
            Product product = new Product(Integer.parseInt(id), name, Double.parseDouble(price), Integer.parseInt(amount), info);
            this.productDao.update(product);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Find all existing products in the database
     *
     * @return List<Product> of all products.
     */
    public List<Product> findAllProducts() {
        try {
            return this.productDao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find a specific product from the database.
     *
     * @param id primary key for the product
     * @return product or null if not found.
     */
    public Product findProduct(Integer id) {
        try {
            return this.productDao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Delete a specific user from the database
     *
     * @param id primary key for the product
     */
    public void deleteProduct(Integer id) {
        try {
            this.productDao.delete(id);
        } catch (Exception e) {
        }
    }

    /**
     * Find all the purchases that a specific user has made.
     *
     * @param id primary key for the user.
     * @return a LinkedMap where the product is the key and purchase time is the
     * value.
     */
    public Map findUsersPurchaseHistory(Integer id) {
        try {
            return this.productDao.findUserHistory(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Save a new purchase or update an existing one in the database
     *
     * @param purchase purchase as a object.
     * @return true if successful and false if not successful
     */
    public boolean saveOrUpdatePurchase(Purchase purchase) {

        try {
            this.purchaseDao.save(purchase);
            Double newBalance = purchase.getUser().getBalance() - purchase.getProduct().getPrice();
            purchase.getUser().setBalance(newBalance);
            this.userDao.update(purchase.getUser());
            int newAmount = purchase.getProduct().getAmount() - 1;
            purchase.getProduct().setAmount(newAmount);
            this.productDao.update(purchase.getProduct());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Find all purchases found in the database
     *
     * @return a list of all purchases.
     */
    public List<Purchase> findAllPurchases() {
        try {
            return this.purchaseDao.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Find a specific purchase from the database
     *
     * @param userId primary key for the user
     * @param productId primary key for the product
     * @return
     */
    public Purchase findPurchase(Integer userId, Integer productId) {
        try {
            return this.purchaseDao.findOne(userId, productId);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Calculates the overall balance from all of the users in the database.
     *
     * @return overall balance as a double.
     */
    public Double calculateOverallBalance() {
        List<User> users = this.findAllUsers();
        return stats.calculateOverallBalance(users);
    }

    /**
     * Calculates the average balance from all of the users in the database.
     *
     * @return the average balance as a double.
     */
    public Double calculateAverageBalance() {
        List<User> users = this.findAllUsers();
        return stats.calculateAverageBalance(users);
    }

}
