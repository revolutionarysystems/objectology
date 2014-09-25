
package uk.co.revsys.objectology.action.handler;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.RemoveFromCollectionAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class RemoveFromCollectionActionHandlerTest {

    public RemoveFromCollectionActionHandlerTest() {
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
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyObjectServiceFactory.setOlogyInstanceService(mockInstanceService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("collection", new CollectionTemplate(new PropertyTemplate()));
        OlogyInstance instance = new OlogyInstance(template);
        Collection collection = new Collection();
        collection.add(new Property("m1"));
        collection.add(new Property("m2"));
        collection.add(new Property("m3"));
        instance.setAttribute("collection", collection);
        RemoveFromCollectionAction action = new RemoveFromCollectionAction("collection", "item");
        RemoveFromCollectionActionHandler handler = new RemoveFromCollectionActionHandler(new JsonInstanceMapper(null));
        ActionRequest request = new ActionRequest();
        request.setParameter("item", "m2");
        Capture<OlogyInstance> updateCapture = new Capture<OlogyInstance>();
        EasyMock.expect(mockInstanceService.update(EasyMock.capture(updateCapture))).andReturn(null);
        mocksControl.replay();
        handler.invoke(instance, action, request);
        Collection updatedCollection = updateCapture.getValue().getAttribute("collection", Collection.class);
        assertEquals(2, updatedCollection.size());
        assertFalse(updatedCollection.contains(new Property("m2")));
        mocksControl.verify();
    }

}