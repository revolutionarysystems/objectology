
package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONOlogyTemplateDeserialiserTest {

    public JSONOlogyTemplateDeserialiserTest() {
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
		ObjectMapper objectMapper = new ObjectMapper(null, new DefaultJSONDeserialiserFactory(null));
		String json = "{\"id\":\"1234\",\"nature\":\"uk.co.revsys.objectology.model.template.OlogyTemplate\",\"attributes\":{\"startTime\":{\"nature\":\"uk.co.revsys.objectology.model.template.TimeTemplate\"},\"limit\":{\"nature\":\"uk.co.revsys.objectology.model.template.MeasurementTemplate\"},\"limits\":{\"memberTemplate\":{\"nature\":\"uk.co.revsys.objectology.model.template.MeasurementTemplate\"},\"nature\":\"uk.co.revsys.objectology.model.template.CollectionTemplate\"},\"status\":{\"nature\":\"uk.co.revsys.objectology.model.template.PropertyTemplate\",\"value\":\"{status}\"},\"accountHolder\":{\"nature\":\"uk.co.revsys.objectology.model.template.OlogyTemplate\",\"attributes\":{\"permissions\":{\"nature\":\"uk.co.revsys.objectology.model.template.PropertyTemplate\"},\"user\":{\"nature\":\"uk.co.revsys.objectology.model.template.LinkTemplate\"}}},\"features\":{\"memberTemplate\":{\"nature\":\"uk.co.revsys.objectology.model.template.OlogyTemplate\",\"attributes\":{\"name\":{\"nature\":\"uk.co.revsys.objectology.model.template.PropertyTemplate\"}}},\"nature\":\"uk.co.revsys.objectology.model.template.CollectionTemplate\"}},\"type\":\"subscription\"}";
		OlogyTemplate result = objectMapper.deserialise(json, OlogyTemplate.class);
		assertNotNull(result);
		assertEquals("subscription", result.getType());
		assertTrue(result.getAttributeTemplate("status") instanceof PropertyTemplate);
		assertTrue(result.getAttributeTemplate("startTime") instanceof TimeTemplate);
		assertEquals("{status}", ((Property)result.getAttributeTemplate("status", PropertyTemplate.class).getValue()).getValue());
		assertTrue(result.getAttributeTemplate("limit") instanceof MeasurementTemplate);
		assertTrue(result.getAttributeTemplate("accountHolder") instanceof OlogyTemplate);
		assertNotNull(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("permissions"));
		assertTrue(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("user") instanceof LinkTemplate);
		assertTrue(result.getAttributeTemplate("limits") instanceof CollectionTemplate);
		assertEquals(MeasurementTemplate.class, result.getAttributeTemplate("limits", CollectionTemplate.class).getMemberTemplate().getClass());
	}

}