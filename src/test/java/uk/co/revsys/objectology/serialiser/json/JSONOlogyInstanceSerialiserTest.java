
package uk.co.revsys.objectology.serialiser.json;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONOlogyInstanceSerialiserTest {

    public JSONOlogyInstanceSerialiserTest() {
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
	 * Test of serialiseJSON method, of class JSONOlogyInstanceSerialiser.
	 */
	@Test
	public void testSerialiseJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper(new DefaultJSONSerialiserFactory(), null);
		OlogyTemplate template = new OlogyTemplate();
		template.setId("1234");
		template.setType("subscription");
		template.getAttributeTemplates().put("status", new PropertyTemplate());
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
		OlogyInstance object = new OlogyInstance();
		object.setTemplate(template);
		object.setAttribute("status", new Property("Created"));
		object.setAttribute("startTime", new Time("01/01/2001 00:00:00"));
		object.setAttribute("limit", new Measurement("1000"));
		Collection limits = new Collection();
		List limitsList = new ArrayList<Measurement>();
		limitsList.add(new Measurement("123"));
		limits.setMembers(limitsList);
		object.setAttribute("limits", limits);
		OlogyInstance partInstance = new OlogyInstance();
		partInstance.setTemplate(partTemplate);
		partInstance.setAttribute("permissions", new Property("all"));
		partInstance.setAttribute("user", new Link("1234"));
		object.setAttribute("accountHolder", partInstance);
		Collection features = new Collection();
		OlogyInstance feature = new OlogyInstance();
		feature.setAttribute("name", new Property("Feature 1"));
		feature.setTemplate(featureTemplate);
		features.getMembers().add(feature);
		object.setAttribute("features", features);
		String json = objectMapper.serialise(object);
		System.out.println("json = " + json);
		JSONObject jsonObject = new JSONObject(json);
		assertEquals("1234", jsonObject.get("template"));
		assertEquals("Created", jsonObject.get("status"));
		assertEquals("01/01/2001 00:00:00", jsonObject.get("startTime"));
		assertEquals("1000", jsonObject.get("limit"));
		assertEquals("all", jsonObject.getJSONObject("accountHolder").getString("permissions"));
		assertEquals("1234", jsonObject.getJSONObject("accountHolder").getString("user"));
		assertEquals(1, jsonObject.getJSONArray("limits").length());
		assertEquals("123", jsonObject.getJSONArray("limits").get(0));
		assertEquals("Feature 1", jsonObject.getJSONArray("features").getJSONObject(0).getString("name"));
	}

}