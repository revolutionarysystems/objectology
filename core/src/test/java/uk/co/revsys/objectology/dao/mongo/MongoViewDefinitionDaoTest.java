package uk.co.revsys.objectology.dao.mongo;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.DuplicateKeyException;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonViewDefinitionMapper;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class MongoViewDefinitionDaoTest {

    public MongoViewDefinitionDaoTest() {
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
        MongoClient mongo = new Fongo("Test Server 1").getMongo();
        JsonObjectMapper jsonMapper = new JsonViewDefinitionMapper();
        MongoViewDefinitionDao dao = new MongoViewDefinitionDao(mongo, "test", jsonMapper);
        ViewDefinition viewDefinition = new ViewDefinition("Test View", "$", new HashMap<String, ViewDefinitionRule>());
        viewDefinition = dao.create(viewDefinition);
        assertNotNull(viewDefinition);
        try {
            viewDefinition = dao.create(viewDefinition);
            fail("Expected DuplicateKeyException to be thrown");
        } catch (DuplicateKeyException ex) {

        }
        ViewDefinition result = dao.findByName("Test View");
        assertNotNull(viewDefinition);
        assertEquals("Test View", result.getName());
    }

}
