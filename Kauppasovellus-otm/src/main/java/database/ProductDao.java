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

        System.out.println("tulostus");
        return tuotteet;
    }

    @Override
    public Product saveOrUpdate(Product object) throws SQLException {
        Product findOne = findOne(object.getId());
        if (findOne == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tuote (nimi, hinta, maara, info) VALUES (?, ?, ?, ?)");
                stmt.setString(1, object.getName());
                stmt.setDouble(2, object.getPrice());
                stmt.setInt(3, object.getAmount());
                stmt.setString(4, object.getInfo());
                stmt.executeUpdate();
            }
            return object;
        } else {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE Tuote SET nimi = ?, hinta = ?, maara = ?, info = ? WHERE tuote_id = ?");
                stmt.setString(1, object.getName());
                stmt.setDouble(2, object.getPrice());
                stmt.setInt(3, object.getAmount());
                stmt.setString(4, object.getInfo());
                stmt.setInt(5, object.getId());
                stmt.executeUpdate();
            }
            return object;
        }
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tuote WHERE tuote_id = ?");
        stmt.setObject(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();    
    }
    
    public void deleteAll() throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tuote");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    public Map<java.time.LocalDateTime, String> findUserHistory(Integer id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT tuote_id, date FROM Ostos WHERE kayttaja_id = ? GROUP BY date ORDER BY date DESC");
        stmt.setObject(1, id);
        ResultSet rs = stmt.executeQuery();
        Map<java.time.LocalDateTime, String> tuotehistoria = new LinkedHashMap<>();
        while (rs.next()) {
            Integer tuote_id = rs.getInt("tuote_id");
            if (this.findOne(tuote_id) != null) {
                String nimi = this.findOne(tuote_id).getName();
                tuotehistoria.put(java.time.LocalDateTime.ofEpochSecond(rs.getInt("date"), 10000, ZoneOffset.ofHours(2)), nimi);
            }
            
        }
        rs.close();
        stmt.close();
        connection.close();
        return tuotehistoria;
    }
}
