package aurinkokuntasimulaattori.domain;

import aurinkokuntasimulaattori.domain.Planet;
import aurinkokuntasimulaattori.math.Vector2;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
    
    BufferedReader reader;
    ArrayList<Planet> planets;
    
    public Loader() {
        this.planets = new ArrayList<Planet>();
    }
    
    public ArrayList readPlanetsFromFile(File file) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            String content = br.readLine();
            while (content != null) {
                String[] osat = content.split(", ");
                if (osat.length == 7) {
                    if (makePlanet(osat) != null) {
                        Planet p = makePlanet(osat);                    
                        this.planets.add(p);
                    }
                }
                content = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the file");
        }
        return this.planets;
    }
    
    private Planet makePlanet(String[] osat) {
        String name = osat[0];
        if (isDouble(osat[1]) && isDouble(osat[2]) && isDouble(osat[3])
                && isDouble(osat[4]) && isDouble(osat[5]) && isDouble(osat[6])) {
            double px = Double.valueOf(osat[1]);
            double py = Double.valueOf(osat[2]);
            double vx = Double.valueOf(osat[3]);
            double vy = Double.valueOf(osat[4]);
            double mass = Double.valueOf(osat[5]);
            double radius = Double.valueOf(osat[6]);
            Vector2 pos = new Vector2(px, py);
            Vector2 vel = new Vector2(vx, vy);

            Planet p = new Planet(name, pos, vel, mass, radius);

            return p;
        } else {
            return null;
        }
    }
    
    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException ne) {
            return false;
        } 
        return true;
    }
    
}