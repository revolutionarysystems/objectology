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
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

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
    public void testInvoke() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyObjectServiceFactory.setOlogyInstanceService(mockService);
        OlogyInstance instance = new OlogyInstance();
        CollectionTemplate collectionTemplate = new CollectionTemplate(new MeasurementTemplate());
        Collection collection = new Collection();
        collection.setTemplate(collectionTemplate);
        instance.setAttribute("collection", collection);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("item", "2");
        AddToCollectionAction action = new AddToCollectionAction("collection", "item");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        AddToCollectionActionHandler actionHandler = new AddToCollectionActionHandler(new JsonInstanceMapper(null));
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals(1, result.getAttribute("collection", Collection.class).getMembers().size());
        assertEquals("2", result.getAttribute("collection", Collection.class).getMembers().get(0).toString());
    }

}
