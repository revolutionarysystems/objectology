
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
import uk.co.revsys.objectology.action.model.CompoundAction;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class CompoundActionHandlerTest {

    public CompoundActionHandlerTest() {
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
        template.setAttributeTemplate("a1", new PropertyTemplate());
        template.setAttributeTemplate("a2", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance(template);
        instance.setAttribute("a1", new Property("b1"));
        instance.setAttribute("a2", new Property("b2"));
        CompoundAction compoundAction = new CompoundAction();
        compoundAction.addAction(new UpdateAttributeAction("a1"));
        compoundAction.addAction(new UpdateAttributeAction("a2"));
        CompoundActionHandler handler = new CompoundActionHandler(new DefaultActionHandlerFactory(new JsonInstanceMapper(null)));
        ActionRequest request = new ActionRequest();
        request.setParameter("a1", "c1");
        request.setParameter("a2", "c2");
        Capture<OlogyInstance> update1Capture = new Capture<OlogyInstance>();
        EasyMock.expect(mockInstanceService.update(EasyMock.capture(update1Capture))).andReturn(instance);
        Capture<OlogyInstance> update2Capture = new Capture<OlogyInstance>();
        EasyMock.expect(mockInstanceService.update(EasyMock.capture(update2Capture))).andReturn(null);
        mocksControl.replay();
        handler.invoke(instance, compoundAction, request);
        assertEquals("c1", update1Capture.getValue().getAttribute("a1", Property.class).getValue());
        assertEquals("c2", update2Capture.getValue().getAttribute("a2", Property.class).getValue());
        mocksControl.verify();
    }

}