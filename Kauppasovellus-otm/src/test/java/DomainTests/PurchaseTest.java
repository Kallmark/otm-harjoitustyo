/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomainTests;

import domain.User;
import domain.Purchase;
import domain.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PurchaseTest {
    
    Product tuote;
    User kayttaja;
    Purchase ostos;

    @Before
    public void setUp() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0);
    }

    
    @Test
    public void valuesAreCorrect() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0);
        assertEquals("Käyttäjä: Testi, tuote: Tuote, aika: 0", ostos.toString());
    }
    
    @Test
    public void returnsTheCorrectUser() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0);
        assertEquals("Testi, id: 1, saldo: 1.0", ostos.getUser().toString());
    }
    
    @Test
    public void returnsTheCorrectProduct() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0);
        assertEquals("Tuote, id: 1, hinta: 1.0, määrä: 1, info: info", ostos.getProduct().toString());
    }
    
    @Test
    public void returnsTheCorrectTime() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0);
        assertEquals(0, ostos.getTime());
    }
    
    @Test
    public void valuesUpdateWorks() {
        Product tuote = new Product(1, "Tuote", 1.0, 1, "info");
        User kayttaja = new User(1, "Testi", 1.0);
        Purchase ostos = new Purchase(kayttaja, tuote, 0); 
        Product tuote2 = new Product(2, "Tuote2", 2.0, 2, "info2");
        User kayttaja2 = new User(2, "Testi2", 2.0);
        ostos.setUser(kayttaja2);
        ostos.setProduct(tuote2);
        ostos.setTime(2);
        assertEquals("Käyttäjä: Testi2, tuote: Tuote2, aika: 2", ostos.toString());
        
    }

    
}
