
package uk.co.revsys.objectology.serialiser.xml;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
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
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public class XMLOlogyInstanceSerialiserTest {

    public XMLOlogyInstanceSerialiserTest() {
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
     * Test of serialiseXML method, of class XMLOlogyInstanceSerialiser.
     */
    @Test
    public void testSerialiseXML() throws Exception {
        XMLObjectMapper objectMapper = new XMLObjectMapper(new DefaultXMLSerialiserFactory(), null);
		OlogyTemplate template = new OlogyTemplate();
		template.setId("1234");
		template.setType("subscription");
		template.getAttributeTemplates().put("status", new PropertyTemplate());
		template.getAttributeTemplates().put("startTime", new TimeTemplate());
		template.getAttributeTemplates().put("limit", new MeasurementTemplate());
        CollectionTemplate limitsTemplate = new CollectionTemplate(new MeasurementTemplate());
		template.getAttributeTemplates().put("limits", limitsTemplate);
		OlogyTemplate partTemplate = new OlogyTemplate();
		partTemplate.getAttributeTemplates().put("permissions", new PropertyTemplate());
		partTemplate.getAttributeTemplates().put("user", new LinkTemplate());
		template.getAttributeTemplates().put("accountHolder", partTemplate);
		OlogyTemplate featureTemplate = new OlogyTemplate();
		featureTemplate.getAttributeTemplates().put("name", new PropertyTemplate());
        CollectionTemplate featuresTemplate = new CollectionTemplate(featureTemplate);
		template.getAttributeTemplates().put("features", featuresTemplate);
		OlogyInstance object = new OlogyInstance();
		object.setTemplate(template);
		object.setAttribute("status", new Property("Created"));
		object.setAttribute("startTime", new Time("01/01/2001 00:00:00"));
		object.setAttribute("limit", new Measurement("1000"));
		Collection limits = new Collection();
        limits.setTemplate(limitsTemplate);
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
        features.setTemplate(featuresTemplate);
		OlogyInstance feature = new OlogyInstance();
		feature.setAttribute("name", new Property("Feature 1"));
		feature.setTemplate(featureTemplate);
		features.getMembers().add(feature);
		object.setAttribute("features", features);
		String xmlString = objectMapper.serialise(object);
        System.out.println("xml = " + xmlString);
        Document xml = DocumentHelper.parseText(xmlString);
        assertEquals("1234", xml.selectSingleNode("/subscription/template").getText());
		assertEquals("Created", xml.selectSingleNode("/subscription/status").getText());
		assertEquals("2001-01-01T00:00:00+0000", xml.selectSingleNode("/subscription/startTime").getText());
		assertEquals("1000", xml.selectSingleNode("/subscription/limit").getText());
		assertEquals("all", xml.selectSingleNode("/subscription/accountHolder/permissions").getText());
		assertEquals("1234", xml.selectSingleNode("/subscription/accountHolder/user").getText());
		assertEquals(1, xml.selectNodes("/subscription/limits/*").size());
		assertEquals("123", xml.selectSingleNode("/subscription/limits/*[1]").getText());
		assertEquals("Feature 1", xml.selectSingleNode("/subscription/features/*[1]/name").getText());
    }

}