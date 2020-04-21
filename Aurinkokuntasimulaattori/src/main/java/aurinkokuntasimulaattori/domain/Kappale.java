package aurinkokuntasimulaattori.domain;

import java.math.*;
import java.util.ArrayList;
import java.util.List;

import aurinkokuntasimulaattori.math.Vector2;

public class Kappale {
    
    private boolean isDeleted = false;
     
    private String name;
    private double mass;
    private double radius;
    
    // Kappaleen nopeusvektori (en ole vielä päättänyt yksikköä mutta m/s luultavimmin)
    private Vector2 vel;
    
    // Kappaleen paikkakoordinaatit (yksikkönä AU)
    private Vector2 pos;
    
    public List<Vector2> oldPos = null;
    
    
    // Kappale ilman nopeutta (testaukseen ns. tippuva kappale)
    public Kappale(Vector2 pos, Vector2 vel, double mass, double radius) {
        this.name = null;
        this.mass = mass;
        this.radius = radius;
        
        this.pos = pos;
        this.vel = vel;
    }
    
    public Kappale(String name, Vector2 pos, double mass, double radius) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        
        this.pos = pos;
        this.vel = new Vector2(0, 0);
    }
    
    public Kappale(String name, Vector2 pos, Vector2 vel, double mass, double radius) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        
        this.pos = pos;
        this.vel = vel;
    }

    // Getterit ja setterit

    public void setMass(int mass) {
        this.mass = mass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public void setPos(Vector2 pos, int tail) {
        if (tail == 0) {
            oldPos = null;
        } else {
            if (oldPos == null) {
                oldPos = new ArrayList<>();
            }
            oldPos.add(0, pos);
            while (oldPos.size() > tail) {
                oldPos.remove(oldPos.size() - 1);
            }
        }
        this.pos = pos;
    }

    public List<Vector2> getOldPos() {
        return oldPos;
    }

    public double getMass() {
        return mass;
    }

    public String getName() {
        return name;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isDeleted() {
        return isDeleted;
    } 
    
    public void delete() {
        this.isDeleted = true;
    }
    
    // Käytännön toiminnallisuudet

    public void merge(Kappale other) {
        other.delete();
        
        this.vel = this.vel.mul(mass);
        this.vel = this.vel.add(other.vel.mul(other.mass).div(mass + other.mass));
        mass += other.mass;
        radius += Math.sqrt(other.radius);
    }

}
