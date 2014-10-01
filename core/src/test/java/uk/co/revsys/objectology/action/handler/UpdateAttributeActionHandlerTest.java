package uk.co.revsys.objectology.action.handler;

import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.revsys.objectology.action.ActionRequest;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.condition.FailedConditionException;
import uk.co.revsys.objectology.condition.IsEqualCondition;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateAttributeActionHandlerTest {

    public UpdateAttributeActionHandlerTest() {
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
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("status", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        instance.setAttribute("status", new Property("enabled"));
        ActionRequest request = new ActionRequest();
        request.getParameters().put("status", "disabled");
        UpdateAttributeAction action = new UpdateAttributeAction("status");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        UpdateAttributeActionHandler actionHandler = new UpdateAttributeActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals("disabled", result.getAttribute("status").toString());
    }

    @Test
    public void testInvokeUpdateObject() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        OlogyTemplate partTemplate = new OlogyTemplate();
        template.setAttributeTemplate("part", partTemplate);
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        partTemplate.setAttributeTemplate("p1", new PropertyTemplate());
        OlogyInstance part = new OlogyInstance();
        part.setTemplate(partTemplate);
        instance.setAttribute("part", part);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("part", "{\"p1\": \"v1\"}");
        UpdateAttributeAction action = new UpdateAttributeAction("part");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        UpdateAttributeActionHandler actionHandler = new UpdateAttributeActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        System.out.println("result = " + result);
        System.out.println("result.getAttribute(\"part\") = " + result.getAttribute("part"));
        System.out.println("result = " + result.getAttribute("part", OlogyInstance.class).getAttribute("p1"));
        assertEquals("v1", result.getAttribute("part", OlogyInstance.class).getAttribute("p1").toString());
    }

    @Test
    public void testInvokeWithSetValue() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("status", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        instance.setAttribute("status", new Property("enabled"));
        ActionRequest request = new ActionRequest();
        request.getParameters().put("status", "disabled");
        UpdateAttributeAction action = new UpdateAttributeAction("status");
        action.setValue("suspended");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        UpdateAttributeActionHandler actionHandler = new UpdateAttributeActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals("suspended", result.getAttribute("status").toString());
    }

    @Test
    public void testInvokeCheckExistingValue() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("status", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        instance.setAttribute("status", new Property("disabled"));
        ActionRequest request = new ActionRequest();
        request.getParameters().put("status", "disabled");
        UpdateAttributeAction action = new UpdateAttributeAction("status");
        action.setValue("suspended");
        action.addCondition(new IsEqualCondition("status", "enabled"));
        UpdateAttributeActionHandler actionHandler = new UpdateAttributeActionHandler(new JsonInstanceMapper());
        try {
            actionHandler.invoke(instance, action, request);
            fail("Expected FailedConditionException to be thrown");
        } catch (ActionInvocationException ex) {
            if (!(ex.getCause() instanceof FailedConditionException)) {
                fail("Expected FailedConditionException to be thrown");
            }
        }
        instance.getAttribute("status", Property.class).setValue("enabled");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals("suspended", result.getAttribute("status").toString());
    }

}
