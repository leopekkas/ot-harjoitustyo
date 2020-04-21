package aurinkokuntasimulaattori.domain;

import aurinkokuntasimulaattori.math.Vector2;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SimulationPhysics {
    
    // Gravitaatiovakio G (AU**3*yr**(-2)*M(earth)**(-1))
    private final double gconstant = 10;
    private final List<Kappale> planets = new ArrayList<>();
    
    public void clear() {
        planets.clear();
    }
    
    public void add(Kappale planet) {
        planets.add(planet);
    }
    
    public Collection<Kappale> getPlanets() {
        return Collections.unmodifiableCollection(planets);
    }
    
    public void step(double timestep, int tail) {
        for (Kappale k : planets) {
            gvuorovaikutus(k, timestep);
        }
        
        updateVel(planets, timestep, tail);
    }
    
    private void gvuorovaikutus(Kappale planet, double timestep) {
        if (planet.isDeleted()) {
            return;
        }
        
        Vector2 force = planets.stream() 
                .map(other -> {
                    if (other != planet && !other.isDeleted()) {
                        Vector2 delta = planet.getPos().substract(other.getPos());
                        
                        // etäisyyden yksikköä AU vastaa simulaatiossa 100 pikseliä
                        double et = delta.abs() * 10; 
                        
                        // F = G * m1 * m2 / r**2
                        double f = -gconstant * (other.getMass() * planet.getMass()) / (et * et);
                        // F = m * a
                        // a:n yksikkö nyt AU/yr**2
                        double acceleration = f / planet.getMass();
                        
                        // Suunnataan kiihtyvyyden suunta oikeaan suuntaan
                        Vector2 force2 = delta.normalize().mul(acceleration);
                        return force2;
                    }
                    return Vector2.ZERO;
                }).reduce(Vector2.ZERO, (accu, value) -> accu.add(value));
        
        planet.setVel(planet.getVel().add(force.mul(timestep))); 
    }
    
    private void updateVel(List<Kappale> planets, double timestep, int tail) {
        Iterator<Kappale> it = planets.iterator();
        while (it.hasNext()) {
            Kappale planet = it.next(); 
            
            if (planet.isDeleted()) {
                it.remove();
            } else {
                planet.setPos(planet.getPos().add(planet.getVel().mul(timestep)), tail);
            }
        }
    }
    
}
