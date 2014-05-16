
package uk.co.revsys.objectology.serialiser.xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

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
		ObjectMapper objectMapper = new ObjectMapper(null, new DefaultXMLDeserialiserFactory(null));
		StringBuilder source = new StringBuilder();
		source.append("<subscription xmlns:o=\"http://test/\">");
		source.append("<status o:nature='property'></status>");
		source.append("<startTime o:nature='time'></startTime>");
		source.append("<limit o:nature='measurement'></limit>");
		source.append("<limits o:nature='collection' o:memberNature='measurement'>");
		source.append("<limit/>");
		source.append("</limits>");
		source.append("<accountHolder o:nature='object'>");
		source.append("<permissions o:nature='property'></permissions>");
		source.append("<user o:nature='link'/>");
		source.append("</accountHolder>");
		source.append("</subscription>");
		OlogyTemplate result = objectMapper.deserialise(source.toString(), OlogyTemplate.class);
		assertNotNull(result);
		assertEquals("subscription", result.getType());
		assertTrue(result.getAttributeTemplate("status") instanceof PropertyTemplate);
		assertTrue(result.getAttributeTemplate("startTime") instanceof TimeTemplate);
		assertTrue(result.getAttributeTemplate("limit") instanceof MeasurementTemplate);
		assertTrue(result.getAttributeTemplate("accountHolder") instanceof OlogyTemplate);
		assertNotNull(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("permissions"));
		assertTrue(result.getAttributeTemplate("accountHolder", OlogyTemplate.class).getAttributeTemplate("user") instanceof LinkTemplate);
		assertTrue(result.getAttributeTemplate("limits") instanceof CollectionTemplate);
		assertEquals(MeasurementTemplate.class, result.getAttributeTemplate("limits", CollectionTemplate.class).getMemberTemplate().getClass());
	}

}