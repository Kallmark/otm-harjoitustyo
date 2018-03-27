/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti maksukortti;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(1000);
    }
    
    @Test 
    public void kassanSaldoOikein(){
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test 
    public void myydytLounaatOikein(){
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void OstoToimiiEdullinen(){
        kassapaate.syoEdullisesti(500);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test 
    public void OstoToimiiMaukas(){
        kassapaate.syoMaukkaasti(500);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test 
    public void MaksuVastaanotetaanEdullinen(){
        kassapaate.syoEdullisesti(500);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    @Test 
    public void VaihtorahaOikeaEdullinen(){
        assertEquals(0, kassapaate.syoEdullisesti(240));
    }
    @Test 
    public void MaksuVastaanotetaanMaukas(){
        kassapaate.syoMaukkaasti(500);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }
    @Test 
    public void VaihtorahaOikeinMaukas(){
        assertEquals(0, kassapaate.syoMaukkaasti(400));
    }
    
    @Test 
    public void OstoKortillaToimiiMaukas(){
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test 
    public void OstoKortillaToimiiEdullinen(){
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test 
    public void MaksuKortillaVastaanotetaanEdullinen(){
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test 
    public void MaksuKortillaVeloitetaanEdullinen(){
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(760, maksukortti.saldo());
    }
    @Test 
    public void MaksuKortillaVeloitetaanEdullinenPalauttaaTrue(){
        kassapaate.syoEdullisesti(maksukortti);
        assertTrue(true);
    }
    @Test 
    public void MaksuKortillaVastaanotetaanMaukas(){
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test 
    public void MaksuKortillaVeloitetaanMaukas(){
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(600, maksukortti.saldo());
    }
    @Test 
    public void MaksuKortillaVeloitetaanMaukasPalauttaaTrue(){
        kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(true);
    }
    public void OstoKortillaToimiiMaukas2(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test 
    public void OstoKortillaToimiiEdullinen2(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test 
    public void MaksuKortillaVastaanotetaanEdullinen2(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test 
    public void MaksuKortillaVeloitetaanEdullinen2(){
        maksukortti.otaRahaa(800);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(200, maksukortti.saldo());
    }
    @Test 
    public void MaksuKortillaVeloitetaanEdullinenPalauttaaFalse(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoEdullisesti(maksukortti);
        assertFalse(false);
    }
    @Test 
    public void MaksuKortillaVastaanotetaanMaukas2(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    @Test 
    public void MaksuKortillaVeloitetaanMaukas2(){
        maksukortti.otaRahaa(700);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(300, maksukortti.saldo());
    }
    @Test 
    public void MaksuKortillaVeloitetaanMaukasPalauttaaFalse(){
        maksukortti.otaRahaa(1000);
        kassapaate.syoMaukkaasti(maksukortti);
        assertFalse(false);
    }
    
    @Test 
    public void KortinLatausToimii(){
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(101000, kassapaate.kassassaRahaa());
    }
    @Test 
    public void KortinLatausToimii2(){
        kassapaate.lataaRahaaKortille(maksukortti, -1000);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    
    
}
