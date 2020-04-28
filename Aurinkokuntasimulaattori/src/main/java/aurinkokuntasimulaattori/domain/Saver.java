package aurinkokuntasimulaattori.domain;

import aurinkokuntasimulaattori.domain.Planet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import java.util.Collections;

import java.util.ArrayList;

/**
 * Luokka vastaa simulaation senhetkisen tiedon tallennuksesta tekstitiedostoon
 */

public class Saver {
    
    BufferedReader reader;
    FileWriter wr;
    
    public Saver() {
    }
    
    /**
     * Metodilla tallennetaan simulaation senhetkinen data käyttäjän 
     * valitsemaan tiedostoon.
     * 
     * @param planeetat Syötteenä annetaan lista tallennettavista planeetoista
     */
    public void saveSimulationData(ArrayList<Planet> planeetat) {
        FileChooser chooser = new FileChooser();
        File savefile = chooser.showSaveDialog(null);
        try {
            this.wr = new FileWriter(savefile);
            for (int k = 0; k < planeetat.size(); k++) {
                System.out.println(planeetat.get(k).getName());
                String line = writePlanet(planeetat.get(k));
                wr.write(line);
                wr.write(System.getProperty("line.separator"));
            }
            wr.close();
        } catch (IOException ioe) {
            System.out.println("Error");
            ioe.printStackTrace();
        }
        
    }
    
    private String writePlanet(Planet p) {
        String px = String.valueOf(p.getPos().x);
        String py = String.valueOf(p.getPos().y);
        String vx = String.valueOf(p.getVel().x);        
        String vy = String.valueOf(p.getVel().y);
        return p.getName() + ", " + px + ", " + py + ", " +
                vx + ", " + vy + ", " + p.getMass() + ", " + p.getRadius();
    }
}
