
package uk.co.revsys.objectology.model.instance;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeasurementTest {

    public MeasurementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() throws Exception {
        assertTrue(new Measurement(15).equals(15));
        assertTrue(new Measurement(15.4f).equals(15.4f));
        assertTrue(new Measurement(15).equals(new BigDecimal(15)));
        assertTrue(new Measurement(new BigDecimal(15)).equals(new BigDecimal(15)));
        assertTrue(new Measurement(new BigDecimal(15)).equals(15));
        assertTrue(new Measurement(15).equals(new Measurement(15)));
    }

}