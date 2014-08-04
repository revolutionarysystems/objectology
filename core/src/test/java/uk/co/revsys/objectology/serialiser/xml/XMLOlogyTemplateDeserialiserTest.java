
package uk.co.revsys.objectology.serialiser.xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonTemplateMapper;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.mapping.xml.XMLTemplateToJSONConverter;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.security.RoleConstraint;

public class XMLOlogyTemplateDeserialiserTest {

    public XMLOlogyTemplateDeserialiserTest() {
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
	 * Test of deserialise method, of class XMLOlogyTemplateDeserialiser.
	 */
	@Test
	public void testDeserialise() throws Exception {
		ObjectMapper objectMapper = new XMLObjectMapper(new XMLTemplateToJSONConverter(), null, new JsonTemplateMapper(), null);
		StringBuilder source = new StringBuilder();
		source.append("<subscription xmlns:o=\"http://test/\">");
        source.append("<viewConstraints>");
        source.append("<securityConstraint o:nature=\"hasRole\">");
        source.append("<role>test:test</role>");
        source.append("</securityConstraint>");
        source.append("</viewConstraints>");
        source.append("<actions>");
        source.append("<changeStatus o:nature=\"updateAttribute\">");
        source.append("<attribute>status</attribute>");
        source.append("</changeStatus>");
        source.append("</actions>");
        source.append("<attributes>");
        source.append("<account o:nature=\"linkedObject\">");
        source.append("<type>account</type>");
        source.append("<link>subscription</link>");
        source.append("</account>");
		source.append("<status o:nature='property'>Template</status>");
		source.append("<startTime o:nature='time'></startTime>");
		source.append("<limit o:nature='measurement'></limit>");
		source.append("<limits o:nature='collection'>");
		source.append("<memberTemplate o:nature='measurement'/>");
		source.append("</limits>");
		source.append("<accountHolder o:nature='object'>");
        source.append("<attributes>");
		source.append("<permissions o:nature='property'></permissions>");
		source.append("<user o:nature='link' o:assocType='user'/>");
        source.append("</attributes>");
		source.append("</accountHolder>");
        source.append("</attributes>");
		source.append("</subscription>");
		OlogyTemplate result = objectMapper.deserialise(source.toString(), OlogyTemplate.class);
		assertNotNull(result);
		assertEquals("subscription", result.getType());
		assertTrue(result.getAttributeTemplate("status") instanceof PropertyTemplate);
		assertTrue(result.getAttributeTemplate("startTime") instanceof TimeTemplate);
		assertEquals("Template", ((Property)result.getAttributeTemplate("status", PropertyTemplate.class).getValue()).getValue());
		assertTrue(result.getAttributeTemplate("limit") instanceof MeasurementTemplate);
		assertTrue(result.getAttributeTemplate("accountHolder") instanceof OlogyTemplate);
		assertNotNull(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("permissions"));
		assertTrue(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("user") instanceof LinkTemplate);
		assertTrue(result.getAttributeTemplate("limits") instanceof CollectionTemplate);
		assertEquals(MeasurementTemplate.class, result.getAttributeTemplate("limits", CollectionTemplate.class).getMemberTemplate().getClass());
        assertEquals(1, result.getViewConstraints().size());
        assertEquals(RoleConstraint.class, result.getViewConstraints().get(0).getClass());
        assertEquals("test:test", ((RoleConstraint)result.getViewConstraints().get(0)).getRole());
        assertEquals(1, result.getActions().size());
        assertEquals(UpdateAttributeAction.class, result.getActions().get("changeStatus").getClass());
        assertEquals("status", ((UpdateAttributeAction)result.getActions().get("changeStatus")).getAttribute());
        assertEquals("account", result.getAttributeTemplate("account", LinkedObjectTemplate.class).getType());
        assertEquals("subscription", result.getAttributeTemplate("account", LinkedObjectTemplate.class).getLink());
	}

	/**
	 * Test of deserialise method, of class XMLOlogyTemplateDeserialiser.
	 */
	@Test
	public void testDeserialiseNamed() throws Exception {
		ObjectMapper objectMapper = new XMLObjectMapper(new XMLTemplateToJSONConverter(), null, new JsonTemplateMapper(), null);
		StringBuilder source = new StringBuilder();
		source.append("<subscription name=\"Test Template A\" xmlns:o=\"http://test/\">");
		source.append("<status o:nature='property'>Template</status>");
		source.append("</subscription>");
		OlogyTemplate result = objectMapper.deserialise(source.toString(), OlogyTemplate.class);
		assertNotNull(result);
		assertEquals("Test Template A", result.getName());
	}

}