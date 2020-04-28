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
import aurinkokuntasimulaattori.domain.Saver;

public class SaverTest {
    
    Planet p;
    Planet p2;
    Saver saver;
    ArrayList<Planet> lista;
    ArrayList<Planet> lista2;
    File file;
    
    public SaverTest() {
    }
    
    @Before
    public void setUp() {
        Vector2 pos = new Vector2(3.1, -7.3);
        Vector2 vel = new Vector2(2.1, 0);
        this.p = new Planet("Maa", pos, vel, 0, 100);
        this.p2 = new Planet(pos, vel, 0, 100);
        this.lista = new ArrayList();
        this.lista.add(p);
        this.lista2 = new ArrayList();
        this.lista2.add(p2);
        
        this.saver = new Saver();
        this.file = new File("src/test/java/aurinkokuntasimulaattori/testi.txt");
    }
    
    @Test
    public void savePlanetsTest() {
        try {
            PrintWriter pr = new PrintWriter(this.file);
            pr.print("");
            pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        saver.saveSimulationData(this.file, lista);
        String content = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            content = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Error");
        }
        String planeetta = "Maa, 3.1, -7.3, 2.1, 0.0, 0.0, 100.0";
        assertEquals(planeetta, content);
    }
    
    @Test
    public void saveNullPlanetsTest() {
        try {
            PrintWriter pr = new PrintWriter(this.file);
            pr.print("");
            pr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        saver.saveSimulationData(this.file, lista2);
        String content = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            content = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Error");
        }
        String planeetta = "null, 3.1, -7.3, 2.1, 0.0, 0.0, 100.0";
        assertEquals(planeetta, content);
    }
}
