/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */

package entities.users;

import entities.Params;
import java.util.HashMap;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Spider
 */
public class EstimatorTest {
    
    Estimator instance;
    
    public EstimatorTest() {
    }
    
   
    @Before
    public void setUp() {
        instance = new Estimator();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of isPositiveDifference method, of class Estimator.
     */
    @Test(expected=NullPointerException.class)
    public void IsPositiveDifference_NullAsBothArguments_ThrowsException() {
        Double a = null;
        Double b = null;
        instance.isPositiveDifference(a, b);
    }
    
    
    @Test
    public void IsPositiveDifference_ZeroAsBothArguments_ReturnsTrue() {
        Double a = 0.0;
        Double b = 0.0;
        Boolean result = instance.isPositiveDifference(a, b);
        assertTrue(result);
    }
    
    @Test
    public void IsPositiveDifference_NegativeArgsBiggerFirst_ReturnsTrue() {
        Double a = -42.0;
        Double b = -42.5;
        Boolean result = instance.isPositiveDifference(a, b);
        assertTrue(result);
    }
    
        @Test
    public void IsPositiveDifference_NegativeArgsBiggerSecond_ReturnsFalse() {
        Double a = -42.5;
        Double b = -42.0;
        Boolean result = instance.isPositiveDifference(a, b);
        assertFalse(result);
    }
}
