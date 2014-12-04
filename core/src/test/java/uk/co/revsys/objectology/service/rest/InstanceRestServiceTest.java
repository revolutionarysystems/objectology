package uk.co.revsys.objectology.service.rest;

import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.shiro.subject.Subject;
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
import uk.co.revsys.objectology.dao.InMemorySequenceGenerator;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.mapping.xml.XMLInstanceToJSONConverter;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.security.AllowAllAuthorisationHandler;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;
import uk.co.revsys.objectology.service.OlogyTemplateService;
import uk.co.revsys.objectology.service.ViewService;
import uk.co.revsys.objectology.view.View;
import uk.co.revsys.user.manager.test.util.AbstractShiroTest;

public class InstanceRestServiceTest extends AbstractShiroTest {

    private IMocksControl mocksControl;
    private Subject mockSubject;
    
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
        mocksControl = EasyMock.createControl();
        mockSubject = mocksControl.createMock(Subject.class);
        setSubject(mockSubject);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdateFromJSON() throws Exception {
        String type = "subscription";
        String id = "1234";
        String json = "{\"prop2\": \"value3\", \"collection1\": [\"c1\", \"c3\"], \"collection2\": {\"$add\": [\"c5\"]}}";
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyTemplateService mockTemplateService = mocksControl.createMock(OlogyTemplateService.class);
        ServiceFactory.setOlogyTemplateService(mockTemplateService);
        ServiceFactory.setSequenceGenerator(new InMemorySequenceGenerator());
        ViewService mockViewService = mocksControl.createMock(ViewService.class);
        InstanceRestService instanceRestService = new InstanceRestService(mockInstanceService, null, new JsonInstanceMapper(), null, new AllowAllAuthorisationHandler(), null, null, mockViewService);
        OlogyTemplate template = new OlogyTemplate();
        template.setId("abcd");
        template.setAttributeTemplate("prop1", new PropertyTemplate());
        template.setAttributeTemplate("prop2", new PropertyTemplate());
        template.setAttributeTemplate("collection1", new CollectionTemplate(new PropertyTemplate()));
        template.setAttributeTemplate("collection2", new CollectionTemplate(new PropertyTemplate()));
        OlogyInstance instance = new OlogyInstance(template);
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
        expect(mockViewService.transform(isA(OlogyInstance.class), isA(View.class))).andReturn(instance);
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
    public void testUpdateFromXML() throws Exception {
        String type = "subscription";
        String id = "1234";
        String xml = "<subscription xmlns:o=\"http://test/\"><prop2>value3</prop2><collection1><property>c1</property><property>c3</property></collection1><collection2 o:action=\"add\"><property>c5</property></collection2></subscription>";
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyTemplateService mockTemplateService = mocksControl.createMock(OlogyTemplateService.class);
        ServiceFactory.setOlogyTemplateService(mockTemplateService);
        ServiceFactory.setSequenceGenerator(new InMemorySequenceGenerator());
        ViewService mockViewService = mocksControl.createMock(ViewService.class);
        InstanceRestService instanceRestService = new InstanceRestService(mockInstanceService, null, new JsonInstanceMapper(), new XMLInstanceToJSONConverter(mockTemplateService), new AllowAllAuthorisationHandler(), null, null, mockViewService);
        OlogyTemplate template = new OlogyTemplate();
        template.setId("abcd");
        template.setType("subscription");
        template.setAttributeTemplate("prop1", new PropertyTemplate());
        template.setAttributeTemplate("prop2", new PropertyTemplate());
        CollectionTemplate collection1Template = new CollectionTemplate(new PropertyTemplate());
        template.setAttributeTemplate("collection1", collection1Template);
        CollectionTemplate collection2Template = new CollectionTemplate(new PropertyTemplate());
        template.setAttributeTemplate("collection2", collection2Template);
        OlogyInstance instance = new OlogyInstance(template);
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
        expect(mockInstanceService.findById(type, id)).andReturn(instance);
        expect(mockTemplateService.findById("abcd")).andReturn(template);
        expect(mockTemplateService.findById("abcd")).andReturn(template);
        Capture<OlogyInstance> instanceCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(instanceCapture))).andReturn(instance);
        expect(mockViewService.transform(isA(OlogyInstance.class), isA(View.class))).andReturn(instance);
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
//        assertEquals(2, updatedInstance.getAttribute("collection2", Collection.class).getMembers().size());
//        assertEquals("c4", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(0)).getValue());
//        assertEquals("c5", ((Property) updatedInstance.getAttribute("collection2", Collection.class).getMembers().get(1)).getValue());
    }

}
