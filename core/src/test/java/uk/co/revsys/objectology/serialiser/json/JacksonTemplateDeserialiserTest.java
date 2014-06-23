package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.jackson.AttributeTemplateDeserialiser;

public class JacksonTemplateDeserialiserTest {

    public JacksonTemplateDeserialiserTest() {
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
     * Test of doDeserialiseJSON method, of class JSONOlogyTemplateDeserialiser.
     */
    @Test
    public void testDoDeserialiseJSON() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttributeTemplate.class, new AttributeTemplateDeserialiser());
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //String json = "{\"id\":\"1234\",\"nature\":\"object\",\"attributes\":{\"seq\": {\"nature\": \"sequence\", \"name\": \"seq1\", \"length\": 4}, \"startTime\":{\"nature\":\"time\"},\"limit\":{\"nature\":\"measurement\"},\"limits\":{\"memberTemplate\":{\"nature\":\"measurement\"},\"nature\":\"collection\"},\"status\":{\"nature\":\"property\",\"value\":\"{status}\"},\"accountHolder\":{\"nature\":\"object\",\"attributes\":{\"permissions\":{\"nature\":\"property\"},\"user\":{\"nature\":\"link\", \"referenceType\": \"name\", \"associatedType\": \"user\"}}},\"features\":{\"memberTemplate\":{\"nature\":\"object\",\"attributes\":{\"name\":{\"nature\":\"property\"}}},\"nature\":\"collection\"}},\"type\":\"subscription\"}";
        String json = "{\"id\":\"1234\",\"nature\":\"object\",\"attributes\":{\"seq\": {\"nature\": \"sequence\", \"name\": \"seq1\", \"length\": 4}, \"startTime\":{\"nature\":\"time\"},\"limit\":{\"nature\":\"measurement\"},\"limits\":{\"memberTemplate\":{\"nature\":\"measurement\"},\"nature\":\"collection\"},\"status\":{\"nature\":\"property\"},\"accountHolder\":{\"nature\":\"object\",\"attributes\":{\"permissions\":{\"nature\":\"property\"},\"user\":{\"nature\":\"link\", \"referenceType\": \"name\", \"associatedType\": \"user\"}}},\"features\":{\"memberTemplate\":{\"nature\":\"object\",\"attributes\":{\"name\":{\"nature\":\"property\"}}},\"nature\":\"collection\"}},\"type\":\"subscription\"}";
        OlogyTemplate result = objectMapper.readValue(json, OlogyTemplate.class);
        assertNotNull(result);
        assertEquals("subscription", result.getType());
        assertTrue(result.getAttributeTemplate("seq") instanceof SequenceTemplate);
        assertEquals("seq1", result.getAttributeTemplate("seq", SequenceTemplate.class).getName());
        assertEquals(4, result.getAttributeTemplate("seq", SequenceTemplate.class).getLength());
        assertTrue(result.getAttributeTemplate("status") instanceof PropertyTemplate);
        assertTrue(result.getAttributeTemplate("startTime") instanceof TimeTemplate);
        //assertEquals("{status}", ((Property) result.getAttributeTemplate("status", PropertyTemplate.class).getValue()).getValue());
        assertTrue(result.getAttributeTemplate("limit") instanceof MeasurementTemplate);
        assertTrue(result.getAttributeTemplate("accountHolder") instanceof OlogyTemplate);
        assertNotNull(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("permissions"));
        LinkTemplate userLinkTemplate = (LinkTemplate) result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("user");
        assertEquals("user", userLinkTemplate.getAssociatedType());
        assertEquals(ReferenceType.name, userLinkTemplate.getReferenceType());
        assertTrue(result.getAttributeTemplate("limits") instanceof CollectionTemplate);
        assertEquals(MeasurementTemplate.class, result.getAttributeTemplate("limits", CollectionTemplate.class).getMemberTemplate().getClass());
    }

}