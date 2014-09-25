package uk.co.revsys.objectology.action.handler;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.UpdateSelectAction;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.SelectTemplate;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import uk.co.revsys.objectology.action.handler.exception.InvalidStateException;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class UpdateSelectActionHandlerTest {

    private OlogyInstance instance;
    private IMocksControl mocksControl;
    private OlogyInstanceService mockInstanceService;

    public UpdateSelectActionHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws UnexpectedAttributeException, ValidationException {
        mocksControl = EasyMock.createControl();
        mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        OlogyObjectServiceFactory.setOlogyInstanceService(mockInstanceService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("status", new SelectTemplate("Enabled", "Disabled"));
        instance = new OlogyInstance(template);
        instance.setAttribute("status", new Property("Enabled"));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInvoke() throws Exception {
        UpdateSelectAction action = new UpdateSelectAction("status");
        UpdateSelectActionHandler handler = new UpdateSelectActionHandler();
        ActionRequest request = new ActionRequest();
        request.setParameter("status", "Disabled");
        Capture<OlogyInstance> updateCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(updateCapture))).andReturn(null);
        mocksControl.replay();
        handler.invoke(instance, action, request);
        assertEquals("Disabled", updateCapture.getValue().getAttribute("status", Property.class).getValue());
        mocksControl.verify();
    }

    @Test
    public void testInvokeWithRequiredExistingValue() throws Exception {
        UpdateSelectAction action = new UpdateSelectAction("status");
        action.setExistingValue("Enabled");
        UpdateSelectActionHandler handler = new UpdateSelectActionHandler();
        ActionRequest request = new ActionRequest();
        request.setParameter("status", "Disabled");
        Capture<OlogyInstance> updateCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(updateCapture))).andReturn(null);
        mocksControl.replay();
        handler.invoke(instance, action, request);
        assertEquals("Disabled", updateCapture.getValue().getAttribute("status", Property.class).getValue());
        try {
            handler.invoke(instance, action, request);
            fail("Expected InvalidStateException to be thrown");
        } catch (InvalidStateException ex) {
            //pass
        }
        mocksControl.verify();
    }
    
    @Test
    public void testInvokeWithSetValue() throws Exception {
        UpdateSelectAction action = new UpdateSelectAction("status");
        action.setValue("Disabled");
        UpdateSelectActionHandler handler = new UpdateSelectActionHandler();
        ActionRequest request = new ActionRequest();
        request.setParameter("status", "Other");
        Capture<OlogyInstance> updateCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.update(capture(updateCapture))).andReturn(null);
        mocksControl.replay();
        handler.invoke(instance, action, request);
        assertEquals("Disabled", updateCapture.getValue().getAttribute("status", Property.class).getValue());
        mocksControl.verify();
    }

}
