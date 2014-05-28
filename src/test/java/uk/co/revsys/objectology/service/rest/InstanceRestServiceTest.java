package uk.co.revsys.objectology.service.rest;

import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.json.DefaultJSONDeserialiserFactory;
import uk.co.revsys.objectology.serialiser.json.DefaultJSONSerialiserFactory;
import uk.co.revsys.objectology.serialiser.xml.DefaultXMLDeserialiserFactory;
import uk.co.revsys.objectology.serialiser.xml.DefaultXMLSerialiserFactory;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyTemplateService;

public class InstanceRestServiceTest {

    public InstanceRestServiceTest() {
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

    @Test
    public void testUpdateFromJSON() throws DaoException {
        String type = "subscription";
        String id = "1234";
        String json = "{\"prop2\": \"value3\", \"collection1\": [\"c1\", \"c3\"], \"collection2\": {\"$add\": [\"c5\"]}}";
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyTemplateService mockTemplateService = mocksControl.createMock(OlogyTemplateService.class);
        InstanceRestService instanceRestService = new InstanceRestService(mockInstanceService, null, new ObjectMapper(new DefaultJSONSerialiserFactory(), new DefaultJSONDeserialiserFactory(mockTemplateService)), null);
        OlogyTemplate template = new OlogyTemplate();
        template.setId("abcd");
        template.getAttributeTemplates().put("prop1", new PropertyTemplate());
        template.getAttributeTemplates().put("prop2", new PropertyTemplate());
        template.getAttributeTemplates().put("collection1", new CollectionTemplate(new PropertyTemplate()));
        template.getAttributeTemplates().put("collection2", new CollectionTemplate(new PropertyTemplate()));
        OlogyInstance instance = new OlogyInstance();
        instance.setId(id);
        instance.setAttribute("prop1", new Property("value1"));
        instance.setAttribute("prop2", new Property("value2"));
        Collection collection1 = new Collection();
        List members1 = new LinkedList<Property>();
        members1.add(new Property("c1"));
        members1.add(new Property("c2"));
        collection1.setMembers(members1);
        instance.setAttribute("collection1", collection1);
        Collection collection2 = new Collection();
        List members2 = new LinkedList<Property>();
        members2.add(new Property("c4"));
        collection2.setMembers(members2);
        instance.setAttribute("collection2", collection2);
        instance.setTemplate(template);
        expect(mockInstanceService.findById(type, id)).andReturn(instance);
        expect(mockTemplateService.findById("abcd")).andReturn(template);
        Capture<OlogyInstance> instanceCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(instanceCapture))).andReturn(instance);
        mocksControl.replay();
        Response response = instanceRestService.updateFromJSON(type, id, json);
        mocksControl.verify();
        OlogyInstance updatedInstance = instanceCapture.getValue();
        assertEquals(id, updatedInstance.getId());
        assertEquals(template, updatedInstance.getTemplate());
        assertEquals("value1", updatedInstance.getAttribute("prop1", Property.class).getValue());
        assertEquals("value3", updatedInstance.getAttribute("prop2", Property.class).getValue());
        assertEquals(2, updatedInstance.getAttribute("collection1", Collection.class).getMembers().size());
        assertEquals("c1", ((Property) updatedInstance.getAttribute("collection1", Collection.class).getMembers().get(0)).getValue());
        assertEquals("c3", ((Property) updatedInstance.getAttribute("collection1", Collection.class).getMembers().get(1)).getValue());
        assertEquals(2, updatedInstance.getAttribute("collection2", Collection.class).getMembers().size());
        assertEquals("c4", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(0)).getValue());
        assertEquals("c5", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(1)).getValue());
    }
    
    @Test
    public void testUpdateFromXML() throws DaoException {
        String type = "subscription";
        String id = "1234";
        String xml = "<subscription xmlns:o=\"http://test/\"><prop2>value3</prop2><collection1><property>c1</property><property>c3</property></collection1><collection2 o:action=\"add\"><property>c5</property></collection2></subscription>";
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyTemplateService mockTemplateService = mocksControl.createMock(OlogyTemplateService.class);
        InstanceRestService instanceRestService = new InstanceRestService(mockInstanceService, new ObjectMapper(new DefaultXMLSerialiserFactory(), new DefaultXMLDeserialiserFactory(mockTemplateService)), new ObjectMapper(new DefaultJSONSerialiserFactory(), new DefaultJSONDeserialiserFactory(mockTemplateService)), null);
        OlogyTemplate template = new OlogyTemplate();
        template.setId("abcd");
        template.setType("subscription");
        template.getAttributeTemplates().put("prop1", new PropertyTemplate());
        template.getAttributeTemplates().put("prop2", new PropertyTemplate());
        CollectionTemplate collection1Template = new CollectionTemplate(new PropertyTemplate());
        template.getAttributeTemplates().put("collection1", collection1Template);
        CollectionTemplate collection2Template = new CollectionTemplate(new PropertyTemplate());
        template.getAttributeTemplates().put("collection2", collection2Template);
        OlogyInstance instance = new OlogyInstance();
        instance.setId(id);
        instance.setAttribute("prop1", new Property("value1"));
        instance.setAttribute("prop2", new Property("value2"));
        Collection collection1 = new Collection();
        collection1.setTemplate(collection1Template);
        List members1 = new LinkedList<Property>();
        members1.add(new Property("c1"));
        members1.add(new Property("c2"));
        collection1.setMembers(members1);
        instance.setAttribute("collection1", collection1);
        Collection collection2 = new Collection();
        collection2.setTemplate(collection2Template);
        List members2 = new LinkedList<Property>();
        members2.add(new Property("c4"));
        collection2.setMembers(members2);
        instance.setAttribute("collection2", collection2);
        instance.setTemplate(template);
        expect(mockInstanceService.findById(type, id)).andReturn(instance);
        expect(mockTemplateService.findById("abcd")).andReturn(template);
        Capture<OlogyInstance> instanceCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(instanceCapture))).andReturn(instance);
        mocksControl.replay();
        Response response = instanceRestService.updateFromXML(type, id, xml);
        mocksControl.verify();
        OlogyInstance updatedInstance = instanceCapture.getValue();
        assertEquals(id, updatedInstance.getId());
        assertEquals(template, updatedInstance.getTemplate());
        assertEquals("value1", updatedInstance.getAttribute("prop1", Property.class).getValue());
        assertEquals("value3", updatedInstance.getAttribute("prop2", Property.class).getValue());
        assertEquals(2, updatedInstance.getAttribute("collection1", Collection.class).getMembers().size());
        assertEquals("c1", ((Property) updatedInstance.getAttribute("collection1", Collection.class).getMembers().get(0)).getValue());
        assertEquals("c3", ((Property) updatedInstance.getAttribute("collection1", Collection.class).getMembers().get(1)).getValue());
        assertEquals(2, updatedInstance.getAttribute("collection2", Collection.class).getMembers().size());
        assertEquals("c4", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(0)).getValue());
        assertEquals("c5", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(1)).getValue());
    }

}
