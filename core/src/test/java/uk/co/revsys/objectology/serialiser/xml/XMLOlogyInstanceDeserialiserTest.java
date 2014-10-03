
package uk.co.revsys.objectology.serialiser.xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.InMemoryDao;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.mapping.xml.XMLInstanceToJSONConverter;
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
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;
import uk.co.revsys.objectology.service.OlogyTemplateServiceImpl;

public class XMLOlogyInstanceDeserialiserTest {

    public XMLOlogyInstanceDeserialiserTest() {
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
	 * Test of deserialise method, of class XMLOlogyInstanceDeserialiser.
	 */
	@Test
	public void testDeserialise() throws Exception {
		Dao dao = new InMemoryDao();
		OlogyTemplateServiceImpl templateService = new OlogyTemplateServiceImpl(dao);
        ServiceFactory.setOlogyTemplateService(templateService);
		ObjectMapper objectMapper = new XMLObjectMapper(null, new XMLInstanceToJSONConverter(templateService), null, new JsonInstanceMapper());
		OlogyTemplate template = new OlogyTemplate();
		template.setAttributeTemplate("status", new PropertyTemplate());
        template.setAttributeTemplate("ref", new PropertyTemplate());
		template.setAttributeTemplate("startTime", new TimeTemplate());
		template.setAttributeTemplate("limit", new MeasurementTemplate());
		template.setAttributeTemplate("endTime", new TimeTemplate());
        template.setAttributeTemplate("settings", new DictionaryTemplate(new PropertyTemplate()));
        template.setAttributeTemplate("ids", new CollectionTemplate(new MeasurementTemplate()));
		template.setAttributeTemplate("limits", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("users", new LinkedObjectsTemplate("user", "subscription"));
		OlogyTemplate partTemplate = new OlogyTemplate();
		partTemplate.setAttributeTemplate("permissions", new PropertyTemplate());
		partTemplate.setAttributeTemplate("user", new LinkTemplate());
		template.setAttributeTemplate("accountHolder", partTemplate);
		CollectionTemplate features = new CollectionTemplate();
		OlogyTemplate featureTemplate = new OlogyTemplate();
		featureTemplate.setAttributeTemplate("name", new PropertyTemplate());
		features.setMemberTemplate(featureTemplate);
		template.setAttributeTemplate("features", features);
		templateService.create(template);
		StringBuilder source = new StringBuilder();
		source.append("<subscription>");
		source.append("<template>").append(template.getId()).append("</template>");
		source.append("<status>Created</status>");
        source.append("<name>Test Subscription</name>");
		source.append("<startTime>01/01/2001 00:00:00</startTime>");
		source.append("<limit>1000</limit>");
		source.append("<limits>");
		source.append("<limit>1</limit>");
		source.append("</limits>");
		source.append("<features>");
		source.append("<feature>");
		source.append("<name>Feature 1</name>");
		source.append("</feature>");
		source.append("</features>");
		source.append("<other>1000</other>");
		source.append("<accountHolder>");
		source.append("<permissions>all</permissions>");
		source.append("<user>1234</user>");
		source.append("</accountHolder>");
        source.append("<settings>");
        source.append("<s1>foo</s1>");
        source.append("<s2>bar</s2>");
        source.append("</settings>");
		source.append("</subscription>");
		OlogyInstance instance = objectMapper.deserialise(source.toString(), OlogyInstance.class);
		assertNotNull(instance);
		assertEquals(template, instance.getTemplate());
        assertEquals("Test Subscription", instance.getName());
		assertEquals("Created", instance.getAttribute("status", Property.class).getValue());
		assertEquals("2001-01-01T00:00:00+0000", instance.getAttribute("startTime", Time.class).toString());
		assertEquals("1000", instance.getAttribute("limit", Measurement.class).toString());
		assertNull(instance.getAttribute("other"));
		assertNotNull(instance.getAttribute("endTime"));
        assertNotNull(instance.getAttribute("ref"));
        assertNotNull(instance.getAttribute("ids"));
		assertNotNull(instance.getAttribute("accountHolder"));
		assertEquals("all", instance.getAttribute("accountHolder", OlogyInstance.class).getAttribute("permissions", Property.class).getValue());
		assertEquals("1234", instance.getAttribute("accountHolder", OlogyInstance.class).getAttribute("user", Link.class).getReference());
		assertEquals(1, instance.getAttribute("limits", Collection.class).getMembers().size());
		assertEquals("1", ((Measurement)instance.getAttribute("limits", Collection.class).getMembers().get(0)).toString());
		assertEquals("Feature 1", ((OlogyInstance)instance.getAttribute("features", Collection.class).getMembers().get(0)).getName());
        assertEquals(new Property("foo"), instance.getAttribute("settings", Dictionary.class).get("s1"));
        assertEquals(new Property("bar"), instance.getAttribute("settings", Dictionary.class).get("s2"));
        
	}

}