package domain;

import database.KayttajaDao;
import database.OstosDao;
import database.TuoteDao;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Logic {

    private OstosDao ostosdao;
    private TuoteDao tuotedao;
    private KayttajaDao kayttajaDao;

    public Logic(KayttajaDao kayttajaDao, TuoteDao tuotedao, OstosDao ostosdao) {
        this.kayttajaDao = kayttajaDao;
        this.tuotedao = tuotedao;
        this.ostosdao = ostosdao;
    }

    public boolean saveOrUpdateUser(Kayttaja kayttaja) {

        try {
            this.kayttajaDao.saveOrUpdate(kayttaja);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Kayttaja> findAllUsers() {
        try {
            return this.kayttajaDao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    public Kayttaja findUser(Integer id) {
        try {
            return this.kayttajaDao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public Map findTopUsers(long secondsNow, long secondsBefore) {
        try {
            return this.kayttajaDao.findAllTime(secondsNow, secondsBefore);
        } catch (SQLException e) {
            return null;
        }
    }

    public void deleteUser(Integer id) {
        try {
            this.kayttajaDao.delete(id);
        } catch (SQLException e) {
        }
    }

    public boolean saveOrUpdateProduct(Tuote tuote) {

        try {
            this.tuotedao.saveOrUpdate(tuote);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Tuote> findAllProducts() {
        try {
            return this.tuotedao.findAll();
        } catch (SQLException e) {
            return null;
        }
    }

    public Tuote findProduct(Integer id) {
        try {
            return this.tuotedao.findOne(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public void deleteProduct(Integer id) {
        try {
            this.tuotedao.delete(id);
        } catch (Exception e) {
        }
    }

    public Map findUsersProductHistory(Integer id) {
        try {
            return this.tuotedao.findUserHistory(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean saveOrUpdatePurchase(Ostos ostos) {

        try {
            this.ostosdao.saveOrUpdate(ostos);
            Double newBalance = ostos.getKayttaja().getSaldo() - ostos.getTuote().getHinta();
            ostos.getKayttaja().setSaldo(newBalance);
            this.kayttajaDao.saveOrUpdate(ostos.getKayttaja());
            int newAmount = ostos.getTuote().getMaara() - 1;
            this.tuotedao.saveOrUpdate(ostos.getTuote());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Ostos> findAllPurchases() {
        try {
            return this.ostosdao.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Ostos findPurchase(Integer user_id, Integer product_id) {
        try {
            return this.ostosdao.findOne(user_id, product_id);
        } catch (SQLException e) {
            return null;
        }
    }

}
