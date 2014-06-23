
package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.InMemoryOlogyObjectDao;
import uk.co.revsys.objectology.dao.InMemorySequenceGenerator;
import uk.co.revsys.objectology.dao.OlogyObjectDao;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Sequence;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.jackson.ContextualObjectMapper;
import uk.co.revsys.objectology.serialiser.jackson.SequenceDeserialiser;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;
import uk.co.revsys.objectology.service.OlogyTemplateServiceImpl;
import uk.co.revsys.objectology.service.OlogyTemplateValidator;

public class JacksonInstanceDeserialiserTest {

    public JacksonInstanceDeserialiserTest() {
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
	 * Test of deserialiseJSON method, of class JSONOlogyInstanceDeserialiser.
	 */
	@Test
	public void testDeserialiseJSON() throws Exception {
		OlogyObjectDao dao = new InMemoryOlogyObjectDao();
		OlogyTemplateServiceImpl templateService = new OlogyTemplateServiceImpl(dao, new OlogyTemplateValidator());
        OlogyObjectServiceFactory.setOlogyTemplateService(templateService);
		ObjectMapper objectMapper = new ContextualObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Sequence.class, new SequenceDeserialiser(new InMemorySequenceGenerator()));
        objectMapper.registerModule(module);
		OlogyTemplate template = new OlogyTemplate();
		template.setType("subscription");
		PropertyTemplate statusTemplate = new PropertyTemplate();
		template.getAttributeTemplates().put("status", statusTemplate);
        template.getAttributeTemplates().put("seq", new SequenceTemplate("seq1", 4));
		template.getAttributeTemplates().put("startTime", new TimeTemplate());
		template.getAttributeTemplates().put("limit", new MeasurementTemplate());
		template.getAttributeTemplates().put("limits", new CollectionTemplate(new MeasurementTemplate()));
		OlogyTemplate partTemplate = new OlogyTemplate();
		partTemplate.getAttributeTemplates().put("permissions", new PropertyTemplate());
		partTemplate.getAttributeTemplates().put("user", new LinkTemplate());
		template.getAttributeTemplates().put("accountHolder", partTemplate);
		templateService.create(template);
		String json = "{\"id\": \"1234\", \"limit\":\"1000\", \"limits\": [\"123\"], \"startTime\":\"01/01/2001 00:00:00\",\"template\":\"" + template.getId() + "\",\"status\":\"Created\", \"accountHolder\": {\"id\": \"4321\", \"permissions\": \"all\", \"user\": \"1234\"}}";
		OlogyInstance result = objectMapper.readValue(json, OlogyInstance.class);
		assertEquals(template.getId(), result.getTemplate().getId());
		assertEquals("Created", result.getAttribute("status", Property.class).getValue());
        assertEquals("0001", result.getAttribute("seq").toString());
		assertEquals(statusTemplate, result.getAttribute("status").getTemplate());
		assertEquals("2001-01-01T00:00:00+0000", result.getAttribute("startTime", Time.class).toString());
		assertEquals("1000", result.getAttribute("limit", Measurement.class).toString());
		assertEquals("all", result.getAttribute("accountHolder", OlogyInstance.class).getAttribute("permissions", Property.class).getValue());
		assertEquals("1234", result.getAttribute("accountHolder", OlogyInstance.class).getAttribute("user", Link.class).getReference());
		assertEquals("123", ((Measurement)result.getAttribute("limits", Collection.class).getMembers().get(0)).toString());
	}

}