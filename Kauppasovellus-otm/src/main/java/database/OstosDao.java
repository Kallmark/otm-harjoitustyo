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

    public Ostos findOne(Integer kayttaja_id, Integer tuote_id) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ostos WHERE kayttaja_id = ? AND tuote_id = ?");
        stmt.setObject(1, kayttaja_id);
        stmt.setObject(2, tuote_id);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        
        Long aika = rs.getLong("date");
        
        Ostos o = new Ostos(this.kayttajadao.findOne(kayttaja_id), this.tuotedao.findOne(tuote_id), aika);

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
            Integer kayttaja_id = rs.getInt("kayttaja_id");
            Integer tuote_id = rs.getInt("tuote_id");
            Long aika = rs.getLong("date");

            ostokset.add(new Ostos(this.kayttajadao.findOne(kayttaja_id), this.tuotedao.findOne(tuote_id), aika));
        }

        rs.close();
        stmt.close();
        connection.close();

        System.out.println("tulostus");
        return ostokset;
    }

    @Override
    public Ostos saveOrUpdate(Ostos object) throws SQLException {
        Ostos findOne = findOne(object.getKayttaja().getId(), object.getTuote().getId());

        if (findOne == null) {

            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO Ostos (kayttaja_id, tuote_id, date) VALUES (?, ?, ?)");

                stmt.setInt(1, object.getKayttaja().getId());
                stmt.setInt(2, object.getTuote().getId());
                stmt.setLong(3, object.getAika());                
                stmt.executeUpdate();
            }
            return object;
        }
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    @Override
    public Ostos findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
