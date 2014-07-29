
package uk.co.revsys.objectology.mapping.xml;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class XMLTemplateToJSONConverterTest {

    public XMLTemplateToJSONConverterTest() {
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
     * Test of convert method, of class XMLTemplateToJSONConverter.
     */
    @Test
    public void testConvert() throws Exception {
        System.out.println("convert");
        StringBuilder source = new StringBuilder();
		source.append("<subscription xmlns:o=\"http://test/\">");
        source.append("<name>Test Template</name>");
        source.append("<attributes>");
		source.append("<status o:nature='property'>Template</status>");
		source.append("<startTime o:nature='time'></startTime>");
		source.append("<limit o:nature='measurement'></limit>");
		source.append("<limits o:nature='collection'>");
        source.append("<member o:nature='measurement'/>");
		source.append("</limits>");
		source.append("<accountHolder o:nature='object'>");
		source.append("<permissions o:nature='property'></permissions>");
		source.append("<user o:nature='link' o:assocType='user'/>");
		source.append("</accountHolder>");
        source.append("</attributes>");
		source.append("</subscription>");
        XMLTemplateToJSONConverter instance = new XMLTemplateToJSONConverter();
        String result = instance.convert(source.toString());
        System.out.println("result = " + result);
        JSONObject json = new JSONObject(result);
        assertEquals("Test Template", json.getString("name"));
        assertEquals("subscription", json.getString("type"));
        assertEquals("measurement", json.getJSONObject("attributes").getJSONObject("limit").getString("nature"));
        assertEquals("Template", json.getJSONObject("attributes").getJSONObject("status").getString("value"));
    }

}