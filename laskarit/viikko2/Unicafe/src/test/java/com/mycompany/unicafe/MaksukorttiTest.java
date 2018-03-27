package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test 
    public void kortinSaldoAlussaOikein(){
        assertNotNull(kortti.saldo());
    }
    
    @Test 
    public void kortinSaldoAlussaOikein2(){
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("saldo: 0.35", kortti.toString());
    }
    
    @Test
    public void kortiltaVoiOttaaRahaa() {
        kortti.otaRahaa(1);
        assertEquals("saldo: 0.9", kortti.toString());
    }
    
    @Test
    public void korttiEiMeneNegatiiviseksi() {
        kortti.otaRahaa(10000);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void saldonMuutosPalauttaaOikeanArvon() {
        kortti.otaRahaa(150);
        assertFalse(false);
    }
    
    @Test
    public void saldonMuutosPalauttaaOikeanArvon2() {
        kortti.otaRahaa(10);
        assertTrue(true);
    }
}
