
package aurinkokuntasimulaattori;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import aurinkokuntasimulaattori.domain.Planet;
import aurinkokuntasimulaattori.math.Vector2;
import aurinkokuntasimulaattori.domain.Loader;

/**
 *
 * @author lbsarast
 */
public class LoaderTest {
    
    Loader loader;
    File file;
    ArrayList<Planet> lista;
    
    Planet p;
    Planet p2;
    
    public LoaderTest() {
    }
    
    
    @Before
    public void setUp() {
        this.loader = new Loader();
        this.lista = new ArrayList();
        
        Vector2 pos = new Vector2(3.1, -7.3);
        Vector2 pos2 = new Vector2(0.1, -7.3);
        Vector2 vel = new Vector2(2.1, 0);
        this.p = new Planet("Maa", pos, vel, 0.0, 100.0);
        this.p2 = new Planet(pos2, vel, 0.0, 100.0);
        this.file = new File("src/test/java/aurinkokuntasimulaattori/loadtest.txt");
    }
    
    @Test
    public void readFromFileTest() {
        try {
            this.lista = this.loader.readPlanetsFromFile(this.file);
        } catch (IOException ioe) {
            System.out.println("Error");
        }
        Planet t1 = this.lista.get(0);
        Planet t2 = this.lista.get(1);
        
        assertEquals(2, this.lista.size());
        assertEquals("Maa", t1.getName());
        assertEquals("null", t2.getName());
        
        Vector2 vastaavap = new Vector2(3.1, -7.3);
        Vector2 vastaavav = new Vector2(2.1, 0.0);
        assertEquals(true, t1.getPos().equals(vastaavap));
        assertEquals(true, t1.getVel().equals(vastaavav));
        
        assertEquals(0.0, t1.getMass(), 0.0001);
        assertEquals(100.0, t2.getRadius(), 0.0001);
    }

}
