package uk.co.revsys.objectology.action.handler;

import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.AddToCollectionAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;

public class AddToCollectionActionHandlerTest {

    public AddToCollectionActionHandlerTest() {
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
    public void testInvokeAddMeasurement() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        CollectionTemplate collectionTemplate = new CollectionTemplate(new MeasurementTemplate());
        template.setAttributeTemplate("collection", collectionTemplate);
        OlogyInstance instance = new OlogyInstance(template);
        Collection collection = new Collection();
        instance.setAttribute("collection", collection);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("item", "2");
        AddToCollectionAction action = new AddToCollectionAction("collection", "item");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        AddToCollectionActionHandler actionHandler = new AddToCollectionActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals(1, result.getAttribute("collection", Collection.class).getMembers().size());
        assertEquals("2", result.getAttribute("collection", Collection.class).getMembers().get(0).toString());
    }
    
    @Test
    public void testInvokeAddMeasurementToRequestedCollection() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        CollectionTemplate collectionTemplate = new CollectionTemplate(new MeasurementTemplate());
        template.setAttributeTemplate("collection", collectionTemplate);
        OlogyInstance instance = new OlogyInstance(template);
        Collection collection = new Collection();
        instance.setAttribute("collection", collection);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("item", "2");
        request.getParameters().put("collection", "collection");
        AddToCollectionAction action = new AddToCollectionAction("${collection}", "item");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        AddToCollectionActionHandler actionHandler = new AddToCollectionActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals(1, result.getAttribute("collection", Collection.class).getMembers().size());
        assertEquals("2", result.getAttribute("collection", Collection.class).getMembers().get(0).toString());
    }
    
    @Test
    public void testInvokeAddToPart() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        OlogyTemplate partTemplate = new OlogyTemplate();
        CollectionTemplate collectionTemplate = new CollectionTemplate(new MeasurementTemplate());
        partTemplate.setAttributeTemplate("collection", collectionTemplate);
        template.setAttributeTemplate("part", partTemplate);
        OlogyInstance instance = new OlogyInstance(template);
        OlogyInstance part = new OlogyInstance();
        instance.setAttribute("part", part);
        Collection collection = new Collection();
        part.setAttribute("collection", collection);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("item", "2");
        AddToCollectionAction action = new AddToCollectionAction("collection", "item");
        action.setBase("part");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        AddToCollectionActionHandler actionHandler = new AddToCollectionActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals(1, result.getAttribute("part", OlogyInstance.class).getAttribute("collection", Collection.class).getMembers().size());
        assertEquals("2", result.getAttribute("part", OlogyInstance.class).getAttribute("collection", Collection.class).getMembers().get(0).toString());
    }
    
    @Test
    public void testInvokeAddObject() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        OlogyTemplate memberTemplate = new OlogyTemplate();
        memberTemplate.setAttributeTemplate("p1", new PropertyTemplate());
        CollectionTemplate collectionTemplate = new CollectionTemplate(memberTemplate);
        template.setAttributeTemplate("collection", collectionTemplate);
        OlogyInstance instance = new OlogyInstance(template);
        Collection collection = new Collection();
        instance.setAttribute("collection", collection);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("item", "{\"p1\": \"v1\"}");
        AddToCollectionAction action = new AddToCollectionAction("collection", "item");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        AddToCollectionActionHandler actionHandler = new AddToCollectionActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals(1, result.getAttribute("collection", Collection.class).getMembers().size());
        OlogyInstance item = (OlogyInstance) result.getAttribute("collection", Collection.class).getMembers().get(0);
        assertEquals(memberTemplate, item.getTemplate());
        assertEquals("v1", item.getAttribute("p1", Property.class).getValue());
    }

}
