package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.mapping.json.JsonObjectMapper;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.SelectTemplate;

public class JacksonTemplateSerialiserTest {

    public JacksonTemplateSerialiserTest() {
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
    public void testSerialiseJSON() throws Exception {
        ObjectMapper objectMapper = new JsonObjectMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setId("1234");
        template.setType("subscription");
        SelectTemplate statusTemplate = new SelectTemplate();
        statusTemplate.getOptions().add("Active");
        statusTemplate.getOptions().add("Suspended");
        statusTemplate.setValue(new Property("Active"));
        UpdateAttributeAction replacePropertyAction = new UpdateAttributeAction("status");
        template.getActions().put("changeStatus", replacePropertyAction);
        template.setAttributeTemplate("seq", new SequenceTemplate("seq1", 4));
        template.setAttributeTemplate("status", statusTemplate);
        template.setAttributeTemplate("description", new PropertyTemplate());
        template.setAttributeTemplate("startTime", new TimeTemplate());
        template.setAttributeTemplate("limit", new MeasurementTemplate());
        template.setAttributeTemplate("limits", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("account", new LinkedObjectTemplate("account", "subscription"));
        OlogyTemplate partTemplate = new OlogyTemplate();
        partTemplate.setAttributeTemplate("permissions", new PropertyTemplate());
        partTemplate.setAttributeTemplate("user", new LinkTemplate());
        template.setAttributeTemplate("accountHolder", partTemplate);
        OlogyTemplate featureTemplate = new OlogyTemplate();
        featureTemplate.setAttributeTemplate("name", new PropertyTemplate());
        template.setAttributeTemplate("features", new CollectionTemplate(featureTemplate));
        String result = objectMapper.serialise(template);
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
        assertEquals("property", json.getJSONObject("attributes").getJSONObject("description").get("nature"));
        assertEquals("select", json.getJSONObject("attributes").getJSONObject("status").get("nature"));
        assertEquals("Active", json.getJSONObject("attributes").getJSONObject("status").getString("value"));
        assertEquals("measurement", json.getJSONObject("attributes").getJSONObject("limits").getJSONObject("memberTemplate").getString("nature"));
        assertEquals("status", json.getJSONObject("actions").getJSONObject("changeStatus").getString("attribute"));
        assertEquals("account", json.getJSONObject("attributes").getJSONObject("account").getString("type"));
        assertEquals("subscription", json.getJSONObject("attributes").getJSONObject("account").getString("link"));
    }

}
