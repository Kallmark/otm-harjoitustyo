package Database;

import java.util.*;
import java.sql.*;
import Domain.Kayttaja;

public class KayttajaDao implements Dao<Kayttaja, Integer> {

    @Override
    public Kayttaja findOne(Integer key) throws SQLException {
        // ei toteutettu
        return null;
    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
	// ei toteutettu
	return null;
    }

    @Override
    public Kayttaja saveOrUpdate(Kayttaja object) throws SQLException {
        // ei toteutettu
        return null;
    }
  
    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
}
