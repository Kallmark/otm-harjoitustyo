package database;

import database.Dao;
import database.Database;
import java.util.*;
import java.sql.*;
import domain.User;
import domain.User;
import domain.Product;

public class UserDao implements Dao<User, Integer> {

    private Database database;

    /**
     * Luo KayttajaDao-olion.
     *
     * @param database Parametrinä toimii tietokanta, johon tietoa lisätään.
     */
    public UserDao(Database database) {
        this.database = database;
    }

    /**
     * Etsii halutun kayttajan tietokannasta.
     *
     * @param key Etsintä tapahtuu antamalla parametriksi id:n
     * @return Palauttaa löydetyn olion, jos sellainen löytyy.
     * @throws SQLException
     */
    @Override
    public User findOne(Integer key) throws SQLException {

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

        User o = new User(id, nimi, saldo);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    /**
     * Etsii kaikki tietokantaan tallennetut kayttajat
     *
     * @return Palauttaa listan kayttajia.
     * @throws SQLException
     */
    @Override
    public List<User> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kayttaja");

        ResultSet rs = stmt.executeQuery();
        List<User> kayttajat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("kayttaja_id");
            String nimi = rs.getString("nimi");
            Double saldo = rs.getDouble("saldo");

            kayttajat.add(new User(id, nimi, saldo));
        }
        rs.close();
        stmt.close();
        connection.close();
        return kayttajat;
    }

    /**
     * Etsii kaikki ostokset, jotka on tehty annettujen parametrien välisenä
     * aikana. Laskee käyttäjäkohtaisten ostoksien määrän ja luokittelee
     * käyttäjät ostoksien määrän perusteella järjesteykseen suurimmasta
     * alimpaan.
     *
     * @param sekunnitNyt Nykyhetken sekunnit laskettuna 1.1.1970 lukien.
     * @param sekunnitEnnen Sekuntimäärä ennen haluttua aikaa.
     * @return Palauttaa LinkedHashMapin, joka säilyttää lisäysjärjestyksen.
     * @throws SQLException
     */
    public Map<User, Integer> findAllTime(long sekunnitNyt, long sekunnitEnnen) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT kayttaja_id, COUNT(kayttaja_id) AS maara FROM Ostos WHERE date <= ? AND date >= ?  GROUP BY kayttaja_id ORDER BY maara DESC");
        stmt.setObject(1, sekunnitNyt);
        stmt.setObject(2, sekunnitEnnen);
        ResultSet rs = stmt.executeQuery();
        Map<User, Integer> kayttajat = new LinkedHashMap<>();
        while (rs.next()) {
            Integer id = rs.getInt("kayttaja_id");
            if (this.findOne(id) != null) {
                String nimi = this.findOne(id).getName();
                Double saldo = this.findOne(id).getBalance();
                Integer kayttajaMaara = rs.getInt("maara");
                kayttajat.put(new User(id, nimi, saldo), kayttajaMaara);
            }
        }
        rs.close();
        stmt.close();
        connection.close();
        return kayttajat;
    }

    /**
     * Lisää uusia käyttäjiä ttietokantaan ai sellaisen jo ollessa olemassa
     * päivittää olemassaolevaa käyttäjää.
     *
     * @param object Parametrinä luotu käyttäjä-olio.
     * @return
     * @throws SQLException
     */
    @Override
    public User saveOrUpdate(User object) throws SQLException {
        User findOne = findOne(object.getId());
        if (findOne == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kayttaja (nimi, saldo) VALUES (?, ?)");
                stmt.setString(1, object.getName());
                stmt.setDouble(2, object.getBalance());
                stmt.executeUpdate();
            }
            return object;
        } else {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("UPDATE Kayttaja SET nimi = ?, saldo = ? WHERE kayttaja_id = ?");
                stmt.setString(1, object.getName());
                stmt.setDouble(2, object.getBalance());
                stmt.setInt(3, object.getId());
                stmt.executeUpdate();
            }
            return object;
        }
    }
    
    /**
     * Deletes a given user from the database.
     * @param key id of the User
     * @throws SQLException 
     */
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Kayttaja WHERE kayttaja_id = ?");
        stmt.setObject(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();    
    }

    /**
     * Poistaa kaikki taulun tiedot.
     *
     * @throws SQLException
     */
    public void deleteAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Kayttaja");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }


}
