package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;
    Maksukortti toinen;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
        toinen = new Maksukortti(2050);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    // Tästä alkaa tehtävää varten luodut testit
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
        assertEquals("saldo: 20.50", toinen.toString());
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenOikein() {
        kortti.lataaRahaa(200);
        assertEquals("saldo: 2.10", kortti.toString());
        toinen.lataaRahaa(250);
        assertEquals("saldo: 23.0", toinen.toString());
    }
    
    @Test
    public void rahanOttaminenToimii() {
        assertEquals(true, kortti.otaRahaa(10));
        assertEquals("saldo: 0.0", kortti.toString());
        assertEquals(false, kortti.otaRahaa(1));
        assertEquals("saldo: 0.0", kortti.toString());
    }
}
