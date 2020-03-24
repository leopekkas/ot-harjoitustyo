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
 * @author lbsarast
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti toinen;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(2000);
        toinen = new Maksukortti(10);
    }
    
    @Test
    public void alkuRahamaaraJaMyydytOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void käteisOstoOikeinEdullisessa() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());

        kassa.syoEdullisesti(200);
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        kassa.syoEdullisesti(300);
        assertEquals(100480, kassa.kassassaRahaa());
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void käteisOstoOikeinMaukkaassa() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());

        kassa.syoMaukkaasti(200);
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        
        kassa.syoMaukkaasti(600);
        assertEquals(100800, kassa.kassassaRahaa());
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoEdullisessa() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        assertEquals(1760, kortti.saldo());
        
        assertEquals(false, kassa.syoEdullisesti(toinen));
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        assertEquals(10, toinen.saldo());
        
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void korttiOstoMaukkaassa() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        
        assertEquals(1600, kortti.saldo());
        
        assertEquals(false, kassa.syoMaukkaasti(toinen));
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        
        assertEquals(10, toinen.saldo());
        
        assertEquals(100000, kassa.kassassaRahaa());
    }    
    
    @Test
    public void kortilleRahanLataus() {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(2200, kortti.saldo());
        
        assertEquals(100200, kassa.kassassaRahaa());
        
        kassa.lataaRahaaKortille(kortti, -10);
        
        assertEquals(2200, kortti.saldo());
        
        assertEquals(100200, kassa.kassassaRahaa());
    }
}
