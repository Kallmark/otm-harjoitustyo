package database;

import database.Dao;
import database.Database;
import domain.User;
import java.util.*;
import java.sql.*;
import domain.Product;
import java.time.ZoneOffset;

public class ProductDao implements Dao<Product, Integer> {

    private Database database;

    public ProductDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a specific product from the database.
     *
     * @param key primary key for the product
     * @return the product that is searched.
     * @throws SQLException
     */
    @Override
    public Product findOne(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tuote WHERE tuote_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("tuote_id");
        String nimi = rs.getString("nimi");
        Double hinta = rs.getDouble("hinta");
        Integer maara = rs.getInt("maara");
        String info = rs.getString("info");

        Product o = new Product(id, nimi, hinta, maara, info);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    /**
     * Finds all the products from the database.
     *
     * @return List<Product> of all products.
     * @throws SQLException
     */
    @Override
    public List<Product> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tuote");
        ResultSet rs = stmt.executeQuery();
        List<Product> tuotteet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("tuote_id");
            String nimi = rs.getString("nimi");
            Double hinta = rs.getDouble("hinta");
            Integer maara = rs.getInt("maara");
            String info = rs.getString("info");

            tuotteet.add(new Product(id, nimi, hinta, maara, info));
        }
        rs.close();
        stmt.close();
        connection.close();
        return tuotteet;
    }

    /**
     * Saves or a product to the database.
     *
     * @param product product as a object.
     * @return product.
     * @throws SQLException
     */
    @Override
    public Product save(Product product) throws SQLException {
        Product findOne = findOne(product.getId());
        if (findOne == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tuote (nimi, hinta, maara, info) VALUES (?, ?, ?, ?)");
                stmt.setString(1, product.getName());
                stmt.setDouble(2, product.getPrice());
                stmt.setInt(3, product.getAmount());
                stmt.setString(4, product.getInfo());
                stmt.executeUpdate();
            }
            return product;
        } else {
            return this.update(product);
        }
    }

    /**
     * Updates a product in the database.
     *
     * @param product updated product.
     * @return product.
     * @throws SQLException
     */
    @Override
    public Product update(Product product) throws SQLException {
        Product findOne = findOne(product.getId());
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Tuote SET nimi = ?, hinta = ?, maara = ?, info = ? WHERE tuote_id = ?");
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getAmount());
            stmt.setString(4, product.getInfo());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        }
        return product;
    }

    /**
     * Deletes a specific product from the database.
     *
     * @param key primary key of the product.
     * @throws SQLException
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tuote WHERE tuote_id = ?");
        stmt.setObject(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    /**
     * Deletes all the products from the database.
     *
     * @throws SQLException
     */
    public void deleteAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tuote");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    /**
     * Searches all the products that a user has purchased.
     *
     * @param id primary key of the user.
     * @return Map<java.time.LocalDateTime, String> where key is timestamp for
     * the purchase and value name of the product.
     * @throws SQLException
     */
    public Map<java.time.LocalDateTime, String> findUserHistory(Integer id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT tuote_id, date FROM Ostos WHERE kayttaja_id = ? GROUP BY date ORDER BY date DESC");
        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();
        Map<java.time.LocalDateTime, String> tuotehistoria = new LinkedHashMap<>();
        while (rs.next()) {
            Integer tuoteId = rs.getInt("tuote_id");
            if (this.findOne(tuoteId) != null) {
                String nimi = this.findOne(tuoteId).getName();
                tuotehistoria.put(java.time.LocalDateTime.ofEpochSecond(rs.getInt("date"), 100, ZoneOffset.ofHours(3)), nimi);
            }

        }
        rs.close();
        stmt.close();
        connection.close();
        return tuotehistoria;
    }
}
