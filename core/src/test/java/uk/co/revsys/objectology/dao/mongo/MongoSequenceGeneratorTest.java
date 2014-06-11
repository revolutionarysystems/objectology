
package uk.co.revsys.objectology.dao.mongo;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongoSequenceGeneratorTest {

    public MongoSequenceGeneratorTest() {
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

    /**
     * Test of doGetNextSequence method, of class MongoSequenceGenerator.
     */
    @Test
    public void testDoGetNextSequence() throws Exception {
        MongoClient mongo = new Fongo("Test Server").getMongo();
        MongoSequenceGenerator sequenceGenerator = new MongoSequenceGenerator(mongo, "test", "counter");
        assertEquals("1", sequenceGenerator.getNextSequence("seq1"));
        assertEquals("2", sequenceGenerator.getNextSequence("seq1"));
        assertEquals("3", sequenceGenerator.getNextSequence("seq1"));
        assertEquals("1", sequenceGenerator.getNextSequence("seq2"));
        assertEquals("2", sequenceGenerator.getNextSequence("seq2"));
        assertEquals("4", sequenceGenerator.getNextSequence("seq1"));
        assertEquals("0001", sequenceGenerator.getNextSequence("seq3", 4));
        assertEquals("00000002", sequenceGenerator.getNextSequence("seq3", 8));
    }

}