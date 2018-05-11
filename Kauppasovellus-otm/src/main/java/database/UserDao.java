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
     * Creates a UserDao object.
     *
     * @param database
     */
    public UserDao(Database database) {
        this.database = database;
    }

    /**
     * Finds a specific user from the database.
     *
     * @param key primary key of the user.
     * @return user or null if not found.
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
     * Finds all the users from the database.
     *
     * @return List<User> of all users.
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
     * Finds all the purchases a user has made in a given time frame.
     *
     * @param sekunnitNyt Current time as unix seconds counting from 1.1.1970.
     * @param sekunnitEnnen Unix seconds at the start of the time frame.
     * @return LinkedMap<User, Integer> where user is key and value is integer
     * amount of purchases made during the time frame.
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
                kayttajat.put(new User(id, this.findOne(id).getName(), this.findOne(id).getBalance()), rs.getInt("maara"));
            }
        }
        rs.close();
        stmt.close();
        connection.close();
        return kayttajat;
    }

    /**
     * Saves a new user to the database.
     *
     * @param user user as a object.
     * @return user.
     * @throws SQLException
     */
    @Override
    public User save(User user) throws SQLException {
        User findOne = findOne(user.getId());
        if (findOne == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kayttaja (nimi, saldo) VALUES (?, ?)");
                stmt.setString(1, user.getName());
                stmt.setDouble(2, user.getBalance());
                stmt.executeUpdate();
            }
            return user;
        } else {
            return this.update(user);
        }
    }

    /**
     * Updates a user in the database.
     *
     * @param user updated user.
     * @return user.
     * @throws SQLException
     */
    @Override
    public User update(User user) throws SQLException {
        User findOne = findOne(user.getId());
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Kayttaja SET nimi = ?, saldo = ? WHERE kayttaja_id = ?");
            stmt.setString(1, user.getName());
            stmt.setDouble(2, user.getBalance());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        }
        return user;
    }

    /**
     * Deletes a given user from the database.
     *
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
     * Deletes all users from the database.
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
