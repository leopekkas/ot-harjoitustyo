
package aurinkokuntasimulaattori.math;

/**
 * Luokkaa käytetään simulaation tukena vektorilaskennassa.
 */
public class Vector2 {
    
    public static final Vector2 ZERO = new Vector2(0.0, 0.0);
    
    public double x;
    public double y;
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2 add(Vector2 vector) {
        return new Vector2(this.x + vector.x, this.y + vector.y);
    }
    
    public Vector2 add(double amount) {
        return new Vector2(this.x + amount, this.y + amount);
    }
    
    public Vector2 substract(Vector2 vector) {
        return new Vector2(this.x - vector.x, this.y - vector.y);
    }
    
    public Vector2 substract(double amount) {
        return new Vector2(this.x - amount, this.y - amount);
    }
    
    public Vector2 mul(double amount) {
        return new Vector2(this.x * amount, y * amount);
    }
    
    public Vector2 div(double amount) {
        return new Vector2(x / amount, y / amount);
    }
    
    /**
     * Vektorin pistetulon laskemista varten toisen vektorin kanssa
     * 
     * @param vector Syötteenä annettu toinen vektori
     * @return Pistetulon suuruus
     */
    public double dot(Vector2 vector) {
        return this.x * vector.x + this.y * vector.y;
    }
    
    /**
     * Metodi palauttaa vektorin itseisarvon eli sen pituuden
     * 
     * @return Vektorin pituus
     */
    public double abs() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    /**
     * Metodi palauttaa kulman itsensä ja toisen vektorin välillä
     * 
     * @param vector Syötteenä annettu toinen vektori
     * @return Kulman suuruus
     */
    public double angle(Vector2 vector) {
        return Math.atan2(this.y, this.x);
    }
    
    /**
     * Metodi normalisoi itsensä, eli jakaa x- ja y-koordinaattinsa pituudellaan
     * 
     * @return normalisoitu versio vektorista
     */
    public Vector2 normalize() {
        double length = abs();
        return new Vector2(this.x / length, this.y / length);
    }
    
    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }
    
    @Override
    public String toString() {
        return " " + this.x + ", " + this.y + " ";
    }
    
    public static Vector2 ofPolar(double angle, double radius) {
        return new Vector2(radius * Math.cos(angle), radius * Math.sin(angle));
    }
    
}
