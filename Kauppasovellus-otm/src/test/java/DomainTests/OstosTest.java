/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import domain.Kayttaja;
import domain.Ostos;
import domain.Tuote;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kallmark
 */
public class OstosTest {
    
    Tuote tuote;
    Kayttaja kayttaja;
    Ostos ostos;

    @Before
    public void setUp() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
    }

    
    @Test
    public void oikeatArvot() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
        assertEquals("Käyttäjä: Testi, tuote: Tuote, aika: 0", ostos.toString());
    }
    
    @Test
    public void palauttaaOikeanKayttajan() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
        assertEquals("Testi, id: 1, saldo: 1.0", ostos.getKayttaja().toString());
    }
    
    @Test
    public void palauttaaOikeanTuotteen() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
        assertEquals("Tuote, id: 1, hinta: 1.0, määrä: 1, info: info", ostos.getTuote().toString());
    }
    
    @Test
    public void palauttaaOikeanAjan() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
        assertEquals(0, ostos.getAika());
    }
    
    @Test
    public void arvotPaivittyvat() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1.0);
        Ostos ostos = new Ostos(kayttaja, tuote, 0);
        
        Tuote tuote2 = new Tuote(2, "Tuote2", 2.0, 2, "info2");
        Kayttaja kayttaja2 = new Kayttaja(2, "Testi2", 2.0);
        ostos.setKayttaja(kayttaja2);
        ostos.setTuote(tuote2);
        ostos.setAika(2);
        
        assertEquals("Käyttäjä: Testi2, tuote: Tuote2, aika: 2", ostos.toString());
        
    }

    
}
