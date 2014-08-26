package uk.co.revsys.objectology.serialiser.json;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonTemplateMapper;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.security.RoleConstraint;
import uk.co.revsys.objectology.view.View;

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
        ObjectMapper objectMapper = new JsonTemplateMapper();
        String json = "{\"id\":\"1234\",\"name\": \"Test Template\",\"nature\":\"object\", \"views\": {\"summary\": {\"securityConstraints\": [{\"nature\": \"hasRole\", \"role\": \"test:test\"}]}}, \"actions\": {\"changeStatus\": {\"nature\": \"updateAttribute\", \"attribute\": \"status\"}}, \"attributes\":{\"account\": {\"nature\": \"linkedObject\", \"type\":\"account\", \"link\":\"subscription\"}, \"seq\": {\"nature\": \"sequence\", \"name\": \"seq1\", \"length\": 4}, \"startTime\":{\"nature\":\"time\"},\"limit\":{\"nature\":\"measurement\"},\"limits\":{\"memberTemplate\":{\"nature\":\"measurement\"},\"nature\":\"collection\"},\"status\":{\"nature\":\"property\", \"value\": \"{status}\"},\"accountHolder\":{\"nature\":\"object\",\"attributes\":{\"permissions\":{\"nature\":\"property\"},\"user\":{\"nature\":\"link\", \"referenceType\": \"name\", \"associatedType\": \"user\"}}},\"features\":{\"memberTemplate\":{\"nature\":\"object\",\"attributes\":{\"name\":{\"nature\":\"property\"}}},\"nature\":\"collection\"}},\"type\":\"subscription\"}";
        OlogyTemplate result = objectMapper.deserialise(json, OlogyTemplate.class);
        assertNotNull(result);
        assertEquals("subscription", result.getType());
        assertTrue(result.getAttributeTemplate("seq") instanceof SequenceTemplate);
        assertEquals("Test Template", result.getName());
        assertEquals("seq1", result.getAttributeTemplate("seq", SequenceTemplate.class).getName());
        assertEquals(4, result.getAttributeTemplate("seq", SequenceTemplate.class).getLength());
        assertTrue(result.getAttributeTemplate("status") instanceof PropertyTemplate);
        assertTrue(result.getAttributeTemplate("startTime") instanceof TimeTemplate);
        assertEquals("{status}", ((Property) result.getAttributeTemplate("status", PropertyTemplate.class).getValue()).getValue());
        assertTrue(result.getAttributeTemplate("limit") instanceof MeasurementTemplate);
        assertTrue(result.getAttributeTemplate("accountHolder") instanceof OlogyTemplate);
        assertNotNull(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("permissions"));
        LinkTemplate userLinkTemplate = (LinkTemplate) result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("user");
        assertEquals("user", userLinkTemplate.getAssociatedType());
        assertEquals(ReferenceType.name, userLinkTemplate.getReferenceType());
        assertTrue(result.getAttributeTemplate("limits") instanceof CollectionTemplate);
        assertEquals(MeasurementTemplate.class, result.getAttributeTemplate("limits", CollectionTemplate.class).getMemberTemplate().getClass());
        assertEquals(1, result.getActions().size());
        assertEquals(UpdateAttributeAction.class, result.getActions().get("changeStatus").getClass());
        assertEquals("status", ((UpdateAttributeAction)result.getActions().get("changeStatus")).getAttribute());
        assertEquals("account", result.getAttributeTemplate("account", LinkedObjectTemplate.class).getType());
        assertEquals("subscription", result.getAttributeTemplate("account", LinkedObjectTemplate.class).getLink());
        assertTrue(result.getViews().get("summary") instanceof View);
        assertNotNull(result.getViews().get("default"));
        assertNotNull(result.getViews().get("identifier"));
        assertEquals(1, result.getViews().get("summary").getSecurityConstraints().size());
        assertEquals(RoleConstraint.class, result.getViews().get("summary").getSecurityConstraints().get(0).getClass());
        assertEquals("test:test", ((RoleConstraint)result.getViews().get("summary").getSecurityConstraints().get(0)).getRole());
    }

}
