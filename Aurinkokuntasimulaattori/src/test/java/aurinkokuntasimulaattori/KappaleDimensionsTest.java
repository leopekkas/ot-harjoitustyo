/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurinkokuntasimulaattori;

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
public class KappaleDimensionsTest {
    
    @Before
    public void setUp() {
    }

    @Test
    public void kappaleenliikkuminen() {
        Kappale testaus = new Kappale("Maa", 1, 100);
        
        testaus.setVx(10);
        testaus.liiku(1);
        
        assertEquals(11.0, testaus.getX(), 0.01);
        
        Kappale testaus2 = new Kappale("Mars", 1, 1, 0.5, 0.1, 0.2, 0.5);
        
        testaus2.liiku(10.5);
        
        assertEquals(2.6, testaus2.getX(), 0.01);
        assertEquals(5.35, testaus2.getY(), 0.01);
    }
    
    @Test
    public void vuorovaikutusTesti() {
        Kappale eka = new Kappale("Maa", 1, 100);
        Kappale toka = new Kappale("Mars", 1, 100, 2, 0, 0, 0);
        
        eka.gvuorovaikutus(toka, 1);
        
        assertEquals(8.886619E-10, eka.getVx(), 1E-11);
        
        // Yksinkertainen testi, käytännössä myös toisen nopeus muuttuisi
    }
}
