/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurinkokuntasimulaattori;

import aurinkokuntasimulaattori.domain.Kappale;
import aurinkokuntasimulaattori.domain.SimulationPhysics;
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
public class SimPhysicsTest {
    
    SimulationPhysics simulaatio;
    
    public SimPhysicsTest() {
    }
    
    
    @Before
    public void setUp() {
        simulaatio = new SimulationPhysics();
    }

    @Test
    public void addPlanetTest() {
        assertEquals(0, simulaatio.getPlanets().size());
        
        Vector2 vektori = new Vector2(0, 0);
        simulaatio.add(new Kappale("Maa", vektori, vektori, 10, 10));
        
        assertEquals(1, simulaatio.getPlanets().size());
    }
    
    @Test
    public void clearPlanetsTest() {
        simulaatio.clear();
        assertEquals(0, simulaatio.getPlanets().size());
        
        Vector2 vektori = new Vector2(0, 0);
        simulaatio.add(new Kappale("Maa", vektori, vektori, 10, 10));
        simulaatio.clear();
        
        assertEquals(0, simulaatio.getPlanets().size());
    }
}
