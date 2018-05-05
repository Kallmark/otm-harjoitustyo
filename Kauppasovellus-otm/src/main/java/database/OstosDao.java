package database;

import database.Dao;
import database.Database;
import java.util.*;
import java.sql.*;
import domain.Tuote;
import domain.Kayttaja;
import domain.Ostos;
import java.time.Instant;

public class OstosDao implements Dao<Ostos, Integer> {

    private Database database;
    private KayttajaDao kayttajadao;
    private TuoteDao tuotedao;

    public OstosDao(Database database) {
        this.database = database;
        this.kayttajadao = new KayttajaDao(database);
        this.tuotedao = new TuoteDao(database);
    }

    public Ostos findOne(Integer kayttajaId, Integer tuoteId) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ostos WHERE kayttaja_id = ? AND tuote_id = ?");
        stmt.setObject(1, kayttajaId);
        stmt.setObject(2, tuoteId);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Long aika = rs.getLong("date");

        Ostos o = new Ostos(this.kayttajadao.findOne(kayttajaId), this.tuotedao.findOne(tuoteId), aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Ostos> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ostos");

        ResultSet rs = stmt.executeQuery();
        List<Ostos> ostokset = new ArrayList<>();
        while (rs.next()) {
            Integer kayttajaId = rs.getInt("kayttaja_id");
            Integer tuoteId = rs.getInt("tuote_id");
            Long aika = rs.getLong("date");

            ostokset.add(new Ostos(this.kayttajadao.findOne(kayttajaId), this.tuotedao.findOne(tuoteId), aika));
        }

        rs.close();
        stmt.close();
        connection.close();
        return ostokset;
    }

    @Override
    public Ostos saveOrUpdate(Ostos object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ostos (kayttaja_id, tuote_id, date) VALUES (?, ?, ?)");

            stmt.setInt(1, object.getKayttaja().getId());
            stmt.setInt(2, object.getTuote().getId());
            stmt.setLong(3, object.getAika());
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
    public Ostos findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
