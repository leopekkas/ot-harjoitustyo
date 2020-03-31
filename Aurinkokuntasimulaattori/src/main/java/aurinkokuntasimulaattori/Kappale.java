package aurinkokuntasimulaattori;

import java.math.*;

public class Kappale {
    
    /*
    Yksinkertaisuuden takia kappaleen massa ilmoitetaan maan massoissa.
    
    Tulevaisuudessa olisi kiva lisätä mahdollisuus ilmoittaa massa myös Jupiterin
    tai Auringon massoina, mutta maan massoina ilmoittaminen on yleinen käytäntö 
    ja tällä myös vältetään turhan suurien kymmenpotenssien pyörittely laskuissa
    */
     
    String name;
    double mass;
    double radius;
    
    // Kappaleen nopeusvektori (en ole vielä päättänyt yksikköä mutta m/s luultavimmin)
    double vx;
    double vy;
    
    // Kappaleen paikkakoordinaatit (yksikkönä AU)
    double x;
    double y;
    
    
    // Kappale ilman nopeutta (testaukseen ns. tippuva kappale)
    public Kappale(String name, double mass, double radius) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        
        this.vx = 0;
        this.vy = 0;
        
        this.x = 1;
        this.y = 0;
    }
    
    public Kappale(String name, double mass, double radius, 
            double x, double y, double vx, double vy) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;

        this.vx = vx;
        this.vy = vy;

        this.x = x;
        this.y = y;
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

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
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

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    // Käytännön toiminnallisuudet

    public void liiku(double askel) {
        // summataan aiempaan paikkaan nopeus * aika-askeleen pituus
        
        this.x += this.vx * askel;
        this.y += this.vy * askel;
    }
    
    public void gvuorovaikutus(Kappale toinen, double askel) {
        // Aika ilmoitetaan päivinä

        // G ilmaistuna muodossa AU**3/(kg*D**2), jossa D on päivä
        double G = 1.488E-34;

        double dx = toinen.x - x;
        double dy = toinen.y - y;

        // Kappaleiden etäisyyden neliö
        double etnelio = dx * dx + dy * dy;

        double maanMassa = 5.97219E24;

        double voima = G * mass * maanMassa * toinen.mass * maanMassa / etnelio;

        double lambda = Math.acos(0); // dZ = 0
        double phi = Math.atan2(dy, dx);
        double apu = voima / (mass * maanMassa) * askel;

        vx += Math.sin(lambda) * Math.cos(phi) * apu;
        vy += Math.sin(lambda) * Math.sin(phi) * apu;
    }

}
