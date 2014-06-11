
package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONOlogyTemplateSerialiserTest {

    public JSONOlogyTemplateSerialiserTest() {
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
	 * Test of serialiseJSON method, of class JSONOlogyTemplateSerialiser.
	 */
	@Test
	public void testSerialiseJSON() throws SerialiserException {
		ObjectMapper objectMapper = new ObjectMapper(new DefaultJSONSerialiserFactory(), null);
		OlogyTemplate template = new OlogyTemplate();
		template.setId("1234");
		template.setType("subscription");
		PropertyTemplate statusTemplate = new PropertyTemplate();
		Property statusProperty = new Property("{status}");
		statusProperty.setTemplate(statusTemplate);
		statusTemplate.setValue(statusProperty);
        template.getAttributeTemplates().put("seq", new SequenceTemplate("seq1", 4));
		template.getAttributeTemplates().put("status", statusTemplate);
		template.getAttributeTemplates().put("startTime", new TimeTemplate());
		template.getAttributeTemplates().put("limit", new MeasurementTemplate());
		template.getAttributeTemplates().put("limits", new CollectionTemplate(new MeasurementTemplate()));
		OlogyTemplate partTemplate = new OlogyTemplate();
		partTemplate.getAttributeTemplates().put("permissions", new PropertyTemplate());
		partTemplate.getAttributeTemplates().put("user", new LinkTemplate());
		template.getAttributeTemplates().put("accountHolder", partTemplate);
		OlogyTemplate featureTemplate = new OlogyTemplate();
		featureTemplate.getAttributeTemplates().put("name", new PropertyTemplate());
		template.getAttributeTemplates().put("features", new CollectionTemplate(featureTemplate));
		String result = objectMapper.serialise(template, OlogyTemplate.class);
		System.out.println("result = " + result);
		JSONObject json = new JSONObject(result);
		assertEquals("1234", json.get("id"));
		assertEquals("subscription", json.get("type"));
		System.out.println(json.getJSONObject("attributes").getJSONObject("accountHolder").getJSONObject("attributes").getJSONObject("user"));
		assertEquals("id", json.getJSONObject("attributes").getJSONObject("accountHolder").getJSONObject("attributes").getJSONObject("user").getString("referenceType"));
        JSONObject seqJson = json.getJSONObject("attributes").getJSONObject("seq");
        assertEquals("sequence", seqJson.getString("nature"));
        assertEquals("seq1", seqJson.getString("name"));
        assertEquals(4, seqJson.getInt("length"));
		assertEquals("property", json.getJSONObject("attributes").getJSONObject("status").get("nature"));
		assertEquals("{status}", json.getJSONObject("attributes").getJSONObject("status").getString("value"));
		assertEquals("measurement", json.getJSONObject("attributes").getJSONObject("limits").getJSONObject("memberTemplate").getString("nature"));
	}


	/**
	 * Test of serialiseJSON method, of class JSONOlogyTemplateSerialiser.
	 */
	@Test
	public void testSerialiseNamedJSON() throws SerialiserException {
		ObjectMapper objectMapper = new ObjectMapper(new DefaultJSONSerialiserFactory(), null);
		OlogyTemplate template = new OlogyTemplate();
		template.setId("1234");
                template.setName("Test Template X");
		template.setType("subscription");
		String result = objectMapper.serialise(template, OlogyTemplate.class);
		System.out.println("result = " + result);
		JSONObject json = new JSONObject(result);
		assertEquals("1234", json.get("id"));
		assertEquals("subscription", json.get("type"));
		assertEquals("Test Template X", json.get("name"));
	}

}