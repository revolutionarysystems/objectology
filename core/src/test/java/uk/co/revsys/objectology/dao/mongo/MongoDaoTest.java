
package uk.co.revsys.objectology.dao.mongo;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonTemplateMapper;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class MongoDaoTest {

    private IMocksControl mocksControl;
    private MongoClient mongoClient;
    private JsonObjectMapper objectMapper;
    private MongoDao<OlogyTemplate> mongoDao;
    
    public MongoDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mocksControl = EasyMock.createControl();
        mongoClient = new Fongo("Test Mongo Server 1").getMongo();
        objectMapper = new JsonTemplateMapper();
        mongoDao = new MongoDao<OlogyTemplate>(mongoClient, "test", OlogyTemplate.class, objectMapper, "template");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() throws Exception {
        OlogyTemplate template1 = new OlogyTemplate();
        template1.setName("Test Template");
        OlogyTemplate result = mongoDao.create(template1);
        assertNotNull(result);
        String templateId = result.getId();
        assertNotNull(templateId);
        result = mongoDao.findById(templateId);
        assertEquals(templateId, result.getId());
        result = mongoDao.findByName("Test Template");
        assertEquals(templateId, result.getId());
        List<OlogyTemplate> results = mongoDao.findAll();
        assertEquals(1, results.size());
        assertEquals(templateId, results.get(0).getId());
    }

}