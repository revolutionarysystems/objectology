
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
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.action.model.UpdateDictionaryEntryAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateDictionaryEntryActionHandlerTest {

    public UpdateDictionaryEntryActionHandlerTest() {
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
    public void testInvoke() throws Exception{
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockService);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("dict", new DictionaryTemplate(new PropertyTemplate()));
        OlogyInstance instance = new OlogyInstance(template);
        Dictionary dictionary = new Dictionary();
        dictionary.put("key1", new Property("value1"));
        dictionary.put("key2", new Property("value2"));
        instance.setAttribute("dict", dictionary);
        ActionRequest request = new ActionRequest();
        request.getParameters().put("key", "key1");
        request.getParameters().put("value", "other");
        UpdateDictionaryEntryAction action = new UpdateDictionaryEntryAction("dict");
        Capture<OlogyInstance> capture = new Capture<OlogyInstance>();
        expect(mockService.update(capture(capture))).andReturn(null);
        mocksControl.replay();
        UpdateDictionaryEntryActionHandler actionHandler = new UpdateDictionaryEntryActionHandler(new JsonInstanceMapper());
        actionHandler.invoke(instance, action, request);
        mocksControl.verify();
        OlogyInstance result = capture.getValue();
        assertEquals("other", result.getAttribute("dict", Dictionary.class).get("key1").toString());
        assertEquals("value2", result.getAttribute("dict", Dictionary.class).get("key2").toString());
    }

}