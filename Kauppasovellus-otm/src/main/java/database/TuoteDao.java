package database;

import database.Dao;
import database.Database;
import java.util.*;
import java.sql.*;
import domain.Tuote;

public class TuoteDao implements Dao<Tuote, Integer> {

    private Database database;

    public TuoteDao(Database database) {
        this.database = database;
    }

    @Override
    public Tuote findOne(Integer key) throws SQLException {

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

        Tuote o = new Tuote(id, nimi, hinta, maara, info);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Tuote> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Tuote");

        ResultSet rs = stmt.executeQuery();
        List<Tuote> tuotteet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("tuote_id");
            String nimi = rs.getString("nimi");
            Double hinta = rs.getDouble("hinta");
            Integer maara = rs.getInt("maara");
            String info = rs.getString("info");

            tuotteet.add(new Tuote(id, nimi, hinta, maara, info));
        }

        rs.close();
        stmt.close();
        connection.close();

        System.out.println("tulostus");
        return tuotteet;
    }

    @Override
    public Tuote saveOrUpdate(Tuote object) throws SQLException {
        Tuote findOne = findOne(object.getId());
        if (findOne == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tuote (nimi, hinta, maara, info) VALUES (?, ?, ?, ?)");
                stmt.setString(1, object.getNimi());
                stmt.setDouble(2, object.getHinta());
                stmt.setInt(3, object.getMaara());
                stmt.setString(4, object.getInfo());
                stmt.executeUpdate();
            }
            return object;
        } else {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE Tuote SET nimi = ?, hinta = ?, maara = ?, info = ? WHERE tuote_id = ?");
                stmt.setString(1, object.getNimi());
                stmt.setDouble(2, object.getHinta());
                stmt.setInt(3, object.getMaara());
                stmt.setString(4, object.getInfo());
                stmt.setInt(5, object.getId());
                stmt.executeUpdate();
            }
            return object;
        }
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void deleteAll() throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Tuote");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
}
