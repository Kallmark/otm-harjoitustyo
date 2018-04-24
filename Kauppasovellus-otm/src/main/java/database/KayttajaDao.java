package database;

import database.Dao;
import database.Database;
import java.util.*;
import java.sql.*;
import domain.Kayttaja;
import domain.Kayttaja;

public class KayttajaDao implements Dao<Kayttaja, Integer> {

    private Database database;

    public KayttajaDao(Database database) {
        this.database = database;
    }

    @Override
    public Kayttaja findOne(Integer key) throws SQLException {
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja WHERE kayttaja_id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("kayttaja_id");
        String nimi = rs.getString("nimi");
        Double saldo = rs.getDouble("saldo");

        Kayttaja o = new Kayttaja(id, nimi, saldo);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja");

        ResultSet rs = stmt.executeQuery();
        List<Kayttaja> kayttajat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("kayttaja_id");
            String nimi = rs.getString("nimi");
            Double saldo = rs.getDouble("saldo");

            kayttajat.add(new Kayttaja(id, nimi, saldo));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        System.out.println("tulostus");
        return kayttajat;
    }

    @Override
    public Kayttaja saveOrUpdate(Kayttaja object) throws SQLException {
        Kayttaja findOne = findOne(object.getId());

        if (findOne == null) {

            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kayttaja (nimi, saldo) VALUES (?, ?)");
                
                stmt.setString(1, object.getNimi());
                stmt.setDouble(2, object.getSaldo());
                stmt.executeUpdate();
            }
            return object;
        } else {

            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE Kayttaja SET nimi = ?, saldo = ? WHERE kayttaja_id = ?");
                stmt.setString(1, object.getNimi());
                stmt.setDouble(2, object.getSaldo());
                stmt.setInt(3, object.getId());
                stmt.executeUpdate();
            }
            return object;
        }
    }


@Override
        public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
}
