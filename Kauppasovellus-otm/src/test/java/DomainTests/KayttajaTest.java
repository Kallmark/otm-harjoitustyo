/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import domain.Kayttaja;
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
public class KayttajaTest {
    
    Kayttaja kayttaja;

    @Before
    public void setUp() {
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1);
    }

    @Test
    public void konstruktoriLuoKayttajan() {
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1);
        assertEquals("Testi", kayttaja.getNimi());
        
    }
    
    @Test
    public void oikeaIdJaSaldo() {
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1);
        assertEquals("Testi id: 1 saldo: 1", kayttaja.toString());
        
    }
    
    @Test
    public void oikeaIdJaSaldoPaivittyy() {
        Kayttaja kayttaja = new Kayttaja(1, "Testi", 1);
        kayttaja.setId(2);
        kayttaja.setNimi("Testi2");
        kayttaja.setSaldo(2);
        assertEquals("Testi2 id: 2 saldo: 2", kayttaja.toString());
        
    }

    
}
