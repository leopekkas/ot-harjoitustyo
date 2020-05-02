package aurinkokuntasimulaattori.domain;

import aurinkokuntasimulaattori.math.Vector2;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Simulaation fysikaalisista ominaisuuksista vastaava luokka.
 */
public class SimulationPhysics {
    
    // Gravitaatiovakio G (AU**3*yr**(-2)*M(earth)**(-1))
    private final double gconstant = 6.67408E-11;
    private final ArrayList<Planet> planets = new ArrayList<>();
    
    /**
     * Metodilla voidaan tyhjentää simulaatio kappaleista.
     */
    public void clear() {
        planets.clear();
    }
    
    /**
     * Metodia käytetään planeetan lisäämiseen osaksi simulaatiota
     * 
     * @param planet Syötteeksi annettava planeetta
     * 
     */
    public void add(Planet planet) {
        planets.add(planet);
    }
    
    public ArrayList<Planet> getPlanets() {
        return this.planets;
    }
    
    /**
     * Metodi laskee jokaisella kutsumiskerrallaan kappaleen vuorovaikutuksen
     * muihin simulaation planeettoihin ja päivittää niiden nopeusvektoria
     * 
     * @param timestep Laskennassa käytettävän aika-askeleen pituus
     * @param tail Simulaation tallentaman kappaleiden aiempien paikkojen lkm:n ptuus
     * 
     */   
    public void step(double timestep, int tail) {
        for (Planet k : planets) {
            gvuorovaikutus(k, timestep);
        }
        
        updateVel(planets, timestep, tail);
    }

    private void gvuorovaikutus(Planet planet, double timestep) {
        if (planet.isDeleted()) {
            return;
        }
        
        Vector2 force = planets.stream() 
                .map(other -> {
                    if (other != planet && !other.isDeleted()) {
                        Vector2 delta = planet.getPos().substract(other.getPos());
                        
                        // etäisyys = |delta_pos|
                        double et = delta.abs(); 
                        
                        // F = G * m1 * m2 / r**2
                        double f = -gconstant * (other.getMass() * planet.getMass()) / (et * et);
                        // F = m * a
                        // a:n yksikkö nyt m/s**2
                        double acceleration = f / planet.getMass();
                        
                        // Suunnataan kiihtyvyyden suunta oikeaan suuntaan
                        Vector2 force2 = delta.normalize().mul(acceleration);
                        return force2;
                    }
                    return Vector2.ZERO;
                }).reduce(Vector2.ZERO, (accu, value) -> accu.add(value));
        // Yksikkö m/s
        planet.setVel(planet.getVel().add(force.mul(timestep))); 
    }
    
    private void updateVel(List<Planet> planets, double timestep, int tail) {
        Iterator<Planet> it = planets.iterator();
        while (it.hasNext()) {
            Planet planet = it.next(); 
            
            if (planet.isDeleted()) {
                it.remove();
            } else {
                planet.setPos(planet.getPos().add(planet.getVel().mul(timestep)), tail);
            }
        }
    }
    
}
