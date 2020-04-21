/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurinkokuntasimulaattori;

import aurinkokuntasimulaattori.domain.Kappale;
import aurinkokuntasimulaattori.math.Vector2;

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
public class KappaleTest {
    
    Kappale testi1;
    Kappale testi2;
    Kappale testi3;
    Kappale testi4;
    Vector2 paikkavektori1;
    Vector2 paikkavektori2;
    Vector2 nopeusvektori1;
    Vector2 nopeusvektori2;
    
    public KappaleTest() {
        
    }
    
    
    @Before
    public void setUp() {
        paikkavektori1 = new Vector2(500, 500);
        paikkavektori2 = new Vector2(0, 0);
        nopeusvektori1 = new Vector2(10, 10);
        nopeusvektori2 = new Vector2(-5, 3.2);
        testi1 = new Kappale("Maa", paikkavektori1, nopeusvektori1, 10, 10);
        testi2 = new Kappale(paikkavektori1, nopeusvektori1, 15, 100);
        testi3 = new Kappale("Mars", paikkavektori2, nopeusvektori2, 100, 100); 
        testi4 = new Kappale("Paikallaan", paikkavektori2, 100, 100);
    }
    
    @Test
    public void namingTest() {
        assertEquals("Maa", testi1.getName());
        assertEquals(null, testi2.getName());
        testi2.setName("Heips!");
        assertEquals("Heips!", testi2.getName());
    }

    @Test
    public void radiusTest() {
        assertEquals(10, testi1.getRadius(), 0.001);
        assertEquals(100, testi2.getRadius(), 0.001);
        assertEquals(100, testi4.getRadius(), 0.001);
        testi1.setRadius(20);
        assertEquals(20, testi1.getRadius(), 0.001);
        
    }
    
    @Test
    public void massTest() {
        assertEquals(10, testi1.getMass(), 0.001);
        assertEquals(15, testi2.getMass(), 0.001);
        assertEquals(100, testi4.getMass(), 0.001);
        testi1.setMass(20);
        assertEquals(20, testi1.getMass(), 0.001);
    }
    
    @Test
    public void velTest() {
        assertEquals(true, new Vector2(10, 10).equals(testi2.getVel()));
        assertEquals(true, new Vector2(-5, 3.2).equals(testi3.getVel()));
        assertEquals(true, new Vector2(0, 0).equals(testi4.getVel()));
        testi4.setVel(new Vector2(10, 300));
        assertEquals(true, new Vector2(10, 300).equals(testi4.getVel()));
    }
    
    @Test
    public void posTest() {
        assertEquals(true, new Vector2(500, 500).equals(testi2.getPos()));
        assertEquals(true, new Vector2(0, 0).equals(testi3.getPos()));
        assertEquals(true, new Vector2(0, 0).equals(testi4.getPos()));
    }
    
    @Test
    public void setPosTest() {
        testi1.setPos(paikkavektori2, 0);
        // En ihan tykkää tästä testistä, ehkä huono tapa vertailla null-arvoa?
        assertEquals(null, testi1.oldPos);
        
        testi1.setPos(paikkavektori1, 1);
        assertEquals(1, testi1.oldPos.size());
        assertEquals(true, testi1.getPos().equals(paikkavektori1));
        
        testi1.setPos(paikkavektori2, 1);
        assertEquals(1, testi1.oldPos.size());
        assertEquals(true, testi1.getPos().equals(paikkavektori2));
        
        assertEquals(1, testi1.getOldPos().size());
    } 
    
    @Test
    public void deleteTest() {
        assertEquals(false, testi1.isDeleted());
        testi1.delete();
        assertEquals(true, testi1.isDeleted());
        
        assertEquals(false, testi2.isDeleted());
        testi2.delete();
        assertEquals(true, testi2.isDeleted());
    } 
    
    @Test
    public void mergePlanetsTest() {
        testi1.merge(testi2);
        assertEquals(25, testi1.getMass(), 0.01);
        assertEquals(20, testi1.getRadius(), 0.01);
        assertEquals(true, testi2.isDeleted());
    }
}
