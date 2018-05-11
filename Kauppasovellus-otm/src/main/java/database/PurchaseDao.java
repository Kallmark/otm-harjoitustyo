package database;

import database.Dao;
import database.Database;
import java.util.*;
import java.sql.*;
import domain.Product;
import domain.User;
import domain.Purchase;
import java.time.Instant;

public class PurchaseDao implements Dao<Purchase, Integer> {

    private Database database;
    private UserDao userDao;
    private ProductDao productDao;

    public PurchaseDao(Database database) {
        this.database = database;
        this.userDao = new UserDao(database);
        this.productDao = new ProductDao(database);
    }

    public Purchase findOne(Integer userId, Integer productId) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ostos WHERE kayttaja_id = ? AND tuote_id = ?");
        stmt.setObject(1, userId);
        stmt.setObject(2, productId);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Long aika = rs.getLong("date");

        Purchase o = new Purchase(this.userDao.findOne(userId), this.productDao.findOne(productId), aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Purchase> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ostos");

        ResultSet rs = stmt.executeQuery();
        List<Purchase> ostokset = new ArrayList<>();
        while (rs.next()) {
            Integer kayttajaId = rs.getInt("kayttaja_id");
            Integer tuoteId = rs.getInt("tuote_id");
            Long aika = rs.getLong("date");

            ostokset.add(new Purchase(this.userDao.findOne(kayttajaId), this.productDao.findOne(tuoteId), aika));
        }

        rs.close();
        stmt.close();
        connection.close();
        return ostokset;
    }

    @Override
    public Purchase saveOrUpdate(Purchase object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ostos (kayttaja_id, tuote_id, date) VALUES (?, ?, ?)");

            stmt.setInt(1, object.getUser().getId());
            stmt.setInt(2, object.getProduct().getId());
            stmt.setLong(3, object.getTime());
            stmt.executeUpdate();
        }
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    
    public void deleteAll() throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Ostos");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public Purchase findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
