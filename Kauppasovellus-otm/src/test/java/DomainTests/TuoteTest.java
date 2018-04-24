/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

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
public class TuoteTest {
    
    Tuote tuote;

    @Before
    public void setUp() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
    }

    
    @Test
    public void oikeatArvot() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        assertEquals("Tuote, id: 1, hinta: 1.0, määrä: 1, info: info", tuote.toString());    
    }
    
    
    @Test
    public void arvotPaivittyvat() {
        Tuote tuote = new Tuote(1, "Tuote", 1.0, 1, "info");
        tuote.setId(2);
        tuote.setNimi("Tuote2");
        tuote.setHinta(2.0);
        tuote.setMaara(2);
        tuote.setInfo("info2");
        assertEquals("Tuote2, id: 2, hinta: 2.0, määrä: 2, info: info2", tuote.toString());  
        
    }

    
}
