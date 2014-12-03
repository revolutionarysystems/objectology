package uk.co.revsys.objectology.serialiser.json;

import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.InMemoryDao;
import uk.co.revsys.objectology.dao.InMemorySequenceGenerator;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.exception.RequiredAttributeException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.ObjectMapper;
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
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Blob;
import uk.co.revsys.objectology.model.instance.BlobPointer;
import uk.co.revsys.objectology.model.instance.BooleanValue;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;
import uk.co.revsys.objectology.model.template.BlobTemplate;
import uk.co.revsys.objectology.model.template.BooleanTemplate;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.model.template.SelectTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceBundle;
import uk.co.revsys.objectology.service.ServiceFactory;
import uk.co.revsys.objectology.service.OlogyTemplateServiceImpl;

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
        Dao dao = new InMemoryDao();
        OlogyTemplateServiceImpl templateService = new OlogyTemplateServiceImpl(dao);
        ServiceFactory.setOlogyTemplateService(templateService);
        ServiceFactory.setSequenceGenerator(new InMemorySequenceGenerator());
        ObjectMapper objectMapper = new JsonInstanceMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setType("subscription");
        SelectTemplate statusTemplate = new SelectTemplate();
        statusTemplate.getOptions().add("Active");
        statusTemplate.getOptions().add("Suspended");
        statusTemplate.setRawDefaultValue("Active");
        PropertyTemplate refTemplate = new PropertyTemplate();
        refTemplate.setStatic(true);
        refTemplate.setRawDefaultValue("abc123");
        PropertyTemplate descriptionTemplate = new PropertyTemplate();
        descriptionTemplate.setRequired(true);
        template.setAttributeTemplate("ref", refTemplate);
        template.setAttributeTemplate("description", descriptionTemplate);
        template.setAttributeTemplate("status", statusTemplate);
        template.setAttributeTemplate("flag", new BooleanTemplate());
        template.setAttributeTemplate("seq", new SequenceTemplate("seq1", 4));
        template.setAttributeTemplate("startTime", new TimeTemplate());
        template.setAttributeTemplate("limit", new MeasurementTemplate());
        template.setAttributeTemplate("limits", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("ids", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("settings", new DictionaryTemplate(new PropertyTemplate()));
        template.setAttributeTemplate("link1", new LinkTemplate());
        template.setAttributeTemplate("account", new LinkedObjectTemplate("account", "subscription"));
        template.setAttributeTemplate("users", new LinkedObjectsTemplate("user", "subscription"));
        OlogyTemplate partTemplate = new OlogyTemplate();
        partTemplate.setAttributeTemplate("permissions", new PropertyTemplate());
        partTemplate.setAttributeTemplate("user", new LinkTemplate());
        template.setAttributeTemplate("accountHolder", partTemplate);
        OlogyTemplate defaultPartTemplate = new OlogyTemplate();
        defaultPartTemplate.setAttributeTemplate("p1", new PropertyTemplate());
        template.setAttributeTemplate("defaultPart", defaultPartTemplate);
        templateService.create(template);
        String json = "{\"id\": \"1234\", \"ref\": \"xyz987\", \"flag\": false, \"name\": \"Test Instance\", \"limit\": 1000, \"account\":\"456\", \"users\":[\"678\"], \"limits\": [\"123\"], \"startTime\":\"01/01/2001 00:00:00\",\"template\":\"" + template.getId() + "\", \"accountHolder\": {\"id\": \"4321\", \"permissions\": \"all\", \"user\": \"1234\"}}";
        try {
            OlogyInstance result = objectMapper.deserialise(json, OlogyInstance.class);
            fail("Expected DeserialiserException to be thrown");
        } catch (DeserialiserException ex) {
            if (ex.getCause() instanceof RequiredAttributeException) {
                assertEquals("Missing Attribute: description", ex.getCause().getMessage());
            } else {
                throw ex;
            }
        }
        json = "{\"id\": \"1234\", \"description\": \"Test\", \"flag\": false, \"ref\": \"xyz987\", \"name\": \"Test Instance\", \"settings\": {\"s1\": \"foo\", \"s2\": \"bar\"}, \"limit\":1000, \"account\":\"456\", \"users\":[\"678\"], \"limits\": [\"123\"], \"startTime\":978307200000, \"template\":\"" + template.getId() + "\", \"accountHolder\": {\"id\": \"4321\", \"permissions\": \"all\", \"user\": \"1234\"}}";
        OlogyInstance result = objectMapper.deserialise(json, OlogyInstance.class);
        assertEquals("1234", result.getId());
        assertNull(result.getParent());
        assertEquals("Test Instance", result.getName());
        assertEquals(template.getId(), result.getTemplate().getId());
        assertEquals("Active", result.getAttribute("status", Property.class).getValue());
        assertEquals(false, result.getAttribute("flag", BooleanValue.class).getValue());
        assertEquals(result, result.getAttribute("status").getParent());
        assertEquals("0001", result.getAttribute("seq").toString());
        assertNull(result.getAttribute("ref"));
        assertEquals(statusTemplate, result.getAttribute("status").getTemplate());
        assertEquals("2001-01-01T00:00:00+0000", result.getAttribute("startTime", Time.class).toString());
        assertEquals("1000", result.getAttribute("limit", Measurement.class).toString());
        assertNotNull(result.getAttribute("ids", Collection.class));
        assertEquals(result, result.getAttribute("accountHolder").getParent());
        assertEquals("all", result.getAttribute("accountHolder", OlogyInstance.class).getAttribute("permissions", Property.class).getValue());
        assertEquals("1234", result.getAttribute("accountHolder", OlogyInstance.class).getAttribute("user", Link.class).getReference());
        assertEquals("123", ((Measurement) result.getAttribute("limits", Collection.class).getMembers().get(0)).toString());
        assertEquals("account", result.getAttribute("account", LinkedObject.class).getTemplate().getType());
        assertEquals("user", result.getAttribute("users", LinkedObjects.class).getTemplate().getType());
        assertEquals(new Property("foo"), result.getAttribute("settings", Dictionary.class).get("s1"));
        assertEquals(new Property("bar"), result.getAttribute("settings", Dictionary.class).get("s2"));
        assertNotNull(result.getAttribute("defaultPart"));
        assertNotNull(result.getAttribute("defaultPart", OlogyInstance.class).getAttribute("p1"));
    }
    
    @Test
    public void testDeserialiseJSON_instanceBundle() throws Exception {
        Dao dao = new InMemoryDao();
        OlogyTemplateServiceImpl templateService = new OlogyTemplateServiceImpl(dao);
        ServiceFactory.setOlogyTemplateService(templateService);
        ServiceFactory.setSequenceGenerator(new InMemorySequenceGenerator());
        ObjectMapper objectMapper = new JsonInstanceMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("p1", new PropertyTemplate());
        template.setAttributeTemplate("text", new BlobTemplate("text/plain"));
        templateService.create(template);
        String templateId = template.getId();
        String json = "{\"template\": \"" + templateId + "\", \"p1\": \"v1\", \"text\": \"This is a test\"}";
        OlogyInstanceBundle instanceBundle = objectMapper.deserialise(json, OlogyInstanceBundle.class);
        assertEquals("v1", instanceBundle.getInstance().getAttribute("p1").toString());
        BlobPointer blobPointer = instanceBundle.getInstance().getAttribute("text", BlobPointer.class);
        assertNotNull(blobPointer.getId());
        List<Blob> blobs = instanceBundle.getBlobs();
        assertEquals(1, blobs.size());
        Blob blob = blobs.get(0);
        assertEquals(blobPointer.getId(), blob.getId());
        assertEquals("This is a test", IOUtils.toString(blob.getInputStream()));
    }

}
