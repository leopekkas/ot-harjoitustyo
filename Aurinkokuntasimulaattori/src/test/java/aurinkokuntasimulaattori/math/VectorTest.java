/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurinkokuntasimulaattori.math;

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
public class VectorTest {
    
    
    @Before
    public void setUp() {
    }

    @Test
    public void sumTest() {
        Vector2 vector1 = new Vector2(1, 1);
        Vector2 vector2 = new Vector2(2, 8);
        
        Vector2 sum = new Vector2(0, 0);
        sum = vector1.add(vector2);
        
        Vector2 sum2 = new Vector2(3, 9);
        
        assertEquals(true, sum.equals(sum2));
        
        Vector2 sum3 = sum2.add(1);
        Vector2 sum4 = new Vector2(4, 10);
                
        assertEquals(true, sum3.equals(sum4));        
    }
    
    @Test
    public void substractTest() {
        Vector2 vector1 = new Vector2(1, 1);
        Vector2 vector2 = new Vector2(3, 8);
        
        Vector2 sum = vector2.substract(vector1);
        
        Vector2 sum2 = new Vector2(2, 7);
        
        assertEquals(true, sum.equals(sum2));
        
        Vector2 sum3 = sum2.substract(1);
        Vector2 sum4 = new Vector2(1, 6);
                
        assertEquals(true, sum3.equals(sum4));  
    }
    
    @Test
    public void multiplyTest() {
        Vector2 vector1 = new Vector2(10, 10);
        
        Vector2 vector2 = vector1.mul(2.5);
        assertEquals(true, vector2.equals(new Vector2(25, 25)));  
    }
    
    @Test
    public void divisionTest() {
        Vector2 vector1 = new Vector2(10, 10);
        
        Vector2 vector2 = vector1.div(2.5);
        assertEquals(true, vector2.equals(new Vector2(4, 4)));  
    }
    
    @Test
    public void dotTest() {
        Vector2 vector1 = new Vector2(10, 10);
        Vector2 vector2 = new Vector2(2, 3.1);
        
        assertEquals(51, vector1.dot(vector2), 0.001);
    }
    
    @Test
    public void absoluteValueTest() {
        Vector2 vector1 = new Vector2(4, 3);
        
        assertEquals(5, vector1.abs(), 0.00001);
    }
    
    @Test
    public void normalizeTest() {
        // Testissä ongelmana se, että double-arvoja vertaillaan,
        // katsotaan keksinkö jonkun paremman testin
        
        Vector2 vector1 = new Vector2(4, 3);
        Vector2 vector2 = new Vector2(0.8, 0.6);
        Vector2 vector3 = vector1.normalize();
        
        assertEquals(true, vector3.equals(vector2));
    }

}
