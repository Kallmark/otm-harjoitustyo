/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import domain.Product;
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
public class ProductTest {
    
    Product tuote;

    @Before
    public void setUp() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
    }

    
    @Test
    public void valuesAreCorrect() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        assertEquals("Tuote, id: 1, hinta: 1.0, määrä: 1, info: info", tuote.toString());    
    }
    
    
    @Test
    public void valuesUpdateWorks() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        tuote.setId(2);
        tuote.setName("Tuote2");
        tuote.setPrice(2.0);
        tuote.setAmount(2);
        tuote.setInfo("info2");
        assertEquals("Tuote2, id: 2, hinta: 2.0, määrä: 2, info: info2", tuote.toString());  
        
    }

    
}
