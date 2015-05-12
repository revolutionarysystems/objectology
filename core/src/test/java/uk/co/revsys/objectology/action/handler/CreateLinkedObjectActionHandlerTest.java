
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
import uk.co.revsys.objectology.action.model.CreateLinkedObjectAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyTemplateService;
import static org.easymock.EasyMock.*;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

public class CreateLinkedObjectActionHandlerTest {

    public CreateLinkedObjectActionHandlerTest() {
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
    public void testDoInvoke() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyTemplateService mockTemplateService = mocksControl.createMock(OlogyTemplateService.class);
        ServiceFactory.setOlogyTemplateService(mockTemplateService);
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockInstanceService);
        OlogyTemplate accountTemplate = new OlogyTemplate();
        accountTemplate.setType("account");
        OlogyInstance account = new OlogyInstance(accountTemplate);
        account.setId("1234");
        OlogyTemplate userTemplate = new OlogyTemplate();
        userTemplate.setType("user");
        userTemplate.setId("5678");
        userTemplate.setAttributeTemplate("firstName", new PropertyTemplate());
        userTemplate.setAttributeTemplate("surname", new PropertyTemplate());
        userTemplate.setAttributeTemplate("account", new LinkTemplate("account", ReferenceType.id));
        CreateLinkedObjectAction action = new CreateLinkedObjectAction("User Template");
        ActionRequest request = new ActionRequest();
        request.setParameter("user", "{\"firstName\": \"Test\", \"surname\": \"User\"}");
        JsonInstanceMapper instanceMapper = new JsonInstanceMapper();
        CreateLinkedObjectActionHandler actionHandler = new CreateLinkedObjectActionHandler(instanceMapper);
        expect(mockTemplateService.findByName("User Template")).andReturn(userTemplate);
        expect(mockTemplateService.findById("5678")).andReturn(userTemplate);
        Capture<OlogyInstance> userCapture = new Capture<OlogyInstance>();
        expect(mockInstanceService.create(capture(userCapture))).andReturn(null);
        mocksControl.replay();
        actionHandler.invoke(account, action, request);
        OlogyInstance user = userCapture.getValue();
        assertEquals(userTemplate, user.getTemplate());
        assertEquals("user", user.getType());
        assertEquals("Test", user.getAttribute("firstName", Property.class).getValue());
        assertEquals("User", user.getAttribute("surname", Property.class).getValue());
        assertEquals("1234", user.getAttribute("account", Link.class).getReference());
        mocksControl.verify();
    }

}