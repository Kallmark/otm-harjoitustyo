/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import domain.User;
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
public class UserTest {
    
    User kayttaja;

    @Before
    public void setUp() {
        User kayttaja = new User(1, "Testi", 1.0);
    }

    @Test
    public void konstruktoriLuoKayttajan() {
        User kayttaja = new User(1, "Testi", 1.0);
        assertEquals("Testi", kayttaja.getName());
        
    }
    
    @Test
    public void oikeaIdJaSaldo() {
        User kayttaja = new User(1, "Testi", 1.0);
        assertEquals("Testi, id: 1, saldo: 1.0", kayttaja.toString());
        
    }
    
    @Test
    public void oikeaIdJaSaldoPaivittyy() {
        User kayttaja = new User(1, "Testi", 1.0);
        kayttaja.setId(2);
        kayttaja.setName("Testi2");
        kayttaja.setBalance(2.0);
        assertEquals("Testi2, id: 2, saldo: 2.0", kayttaja.toString());
        
    }

    
}
