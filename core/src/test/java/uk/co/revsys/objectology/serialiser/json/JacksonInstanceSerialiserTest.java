package uk.co.revsys.objectology.serialiser.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import org.easymock.IMocksControl;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.mapping.ObjectMapper;
import uk.co.revsys.objectology.mapping.json.JsonDBInstanceMapper;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.LinkedObject;
import uk.co.revsys.objectology.model.instance.LinkedObjects;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.instance.Time;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.model.template.SequenceTemplate;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;

public class JacksonInstanceSerialiserTest {

    public JacksonInstanceSerialiserTest() {
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
    public void testSerialiseJSON() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockInstanceService);
        ObjectMapper objectMapper = new JsonInstanceMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setId("1234");
        template.setType("subscription");
        PropertyTemplate statusTemplate = new PropertyTemplate();
        statusTemplate.setRawDefaultValue("Created");
        template.setAttributeTemplate("status", statusTemplate);
        template.setAttributeTemplate("seq", new SequenceTemplate("seq1", 4));
        template.setAttributeTemplate("startTime", new TimeTemplate());
        template.setAttributeTemplate("limit", new MeasurementTemplate());
        template.setAttributeTemplate("ids", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("limits", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("account", new LinkedObjectTemplate("account", "subscription"));
        LinkedObjectsTemplate linkedUsersTemplate = new LinkedObjectsTemplate("user", "subscription");
        template.setAttributeTemplate("users", linkedUsersTemplate);
        OlogyTemplate partTemplate = new OlogyTemplate();
        partTemplate.setAttributeTemplate("permissions", new PropertyTemplate());
        partTemplate.setAttributeTemplate("user", new LinkTemplate());
        template.setAttributeTemplate("accountHolder", partTemplate);
        OlogyTemplate featureTemplate = new OlogyTemplate();
        featureTemplate.setAttributeTemplate("title", new PropertyTemplate());
        featureTemplate.setAttributeTemplate("name", new PropertyTemplate());
        template.setAttributeTemplate("features", new CollectionTemplate(featureTemplate));
        OlogyInstance object = new OlogyInstance();
        object.setTemplate(template);
        object.setAttribute("status", new Property("Created"));
        object.setAttribute("seq", new Property("0001"));
        object.setAttribute("startTime", new Time("01/01/2001 00:00:00"));
        object.setAttribute("limit", new Measurement("1000"));
        LinkedObject accountLink = new LinkedObject();
        accountLink.setTemplate(new LinkedObjectTemplate("account", "subscription"));
        object.setAttribute("account", accountLink);
        LinkedObjects linkedUsers = new LinkedObjects();
        linkedUsers.setTemplate(linkedUsersTemplate);
        object.setAttribute("users", linkedUsers);
        Collection limits = new Collection();
        List limitsList = new ArrayList<Measurement>();
        limitsList.add(new Measurement("123"));
        limits.setMembers(limitsList);
        object.setAttribute("limits", limits);
        OlogyInstance partInstance = new OlogyInstance();
        partInstance.setTemplate(partTemplate);
        partInstance.setAttribute("permissions", new Property("all"));
        partInstance.setAttribute("user", new Link("1234"));
        object.setAttribute("accountHolder", partInstance);
        Collection features = new Collection();
        OlogyInstance feature = new OlogyInstance();
        feature.setTemplate(featureTemplate);
        feature.setAttribute("title", new Property("Feature 1"));
        features.add(feature);
        object.setAttribute("features", features);
        OlogyTemplate accountTemplate = new OlogyTemplate();
        OlogyInstance account = new OlogyInstance();
        account.setTemplate(accountTemplate);
        account.setId("5678");
        List<OlogyInstance> accounts = new ArrayList<OlogyInstance>();
        accounts.add(account);
        Capture<JSONQuery> queryCapture = new Capture<JSONQuery>();
        expect(mockInstanceService.find(eq("account"), capture(queryCapture))).andReturn(accounts);
        OlogyTemplate userTemplate = new OlogyTemplate();
        OlogyInstance user1 = new OlogyInstance();
        user1.setTemplate(userTemplate);
        user1.setId("user1");
        OlogyInstance user2 = new OlogyInstance();
        user2.setTemplate(userTemplate);
        user2.setId("user2");
        List<OlogyInstance> users = new ArrayList<OlogyInstance>();
        users.add(user1);
        users.add(user2);
        expect(mockInstanceService.find(eq("user"), capture(queryCapture))).andReturn(users);
        mocksControl.replay();
        String json = objectMapper.serialise(object);
        System.out.println("json = " + json);
        mocksControl.verify();
        JSONObject jsonObject = new JSONObject(json);
        assertEquals("1234", jsonObject.get("template"));
        assertEquals("Created", jsonObject.get("status"));
        assertEquals("0001", jsonObject.get("seq"));
        assertEquals("2001-01-01T00:00:00+0000", jsonObject.get("startTime"));
        assertEquals("1000", jsonObject.get("limit"));
        assertEquals("all", jsonObject.getJSONObject("accountHolder").getString("permissions"));
        assertEquals("1234", jsonObject.getJSONObject("accountHolder").getString("user"));
        assertEquals(1, jsonObject.getJSONArray("limits").length());
        assertEquals("123", jsonObject.getJSONArray("limits").get(0));
        assertEquals("Feature 1", jsonObject.getJSONArray("features").getJSONObject(0).getString("title"));
        assertEquals("5678", jsonObject.getString("account"));
        assertEquals(2, jsonObject.getJSONArray("users").length());
    }

    @Test
    public void testSerialiseJSONWithDepthParam() throws Exception {
        ObjectMapper objectMapper = new JsonInstanceMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setId("1234");
        LinkTemplate userLinkTemplate = new LinkTemplate("user");
        userLinkTemplate.setReferenceType(ReferenceType.name);
        template.setAttributeTemplate("user", userLinkTemplate);
        LinkedObjectsTemplate accountLinkTemplate = new LinkedObjectsTemplate("account", "subscription");
        template.setAttributeTemplate("accounts", accountLinkTemplate);
        OlogyTemplate userTemplate = new OlogyTemplate();
        OlogyInstance user = new OlogyInstance();
        user.setTemplate(userTemplate);
        user.setName("Test User");
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        Link userLink = new Link("Test User");
        userLink.setTemplate(userLinkTemplate);
        instance.setAttribute("user", userLink);
        LinkedObjects accountLink = new LinkedObjects();
        accountLink.setTemplate(accountLinkTemplate);
        instance.setAttribute("accounts", accountLink);
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockInstanceService);
        OlogyTemplate accountTemplate = new OlogyTemplate();
        accountTemplate.setAttributeTemplate("user", new LinkTemplate());
        OlogyInstance account = new OlogyInstance();
        account.setTemplate(accountTemplate);
        account.setId("5678");
        account.setAttribute("user", new Link("1234"));
        List<OlogyInstance> accounts = new ArrayList<OlogyInstance>();
        accounts.add(account);
        expect(mockInstanceService.findByName("user", "Test User")).andReturn(user);
        expect(mockInstanceService.find(isA(String.class), isA(JSONQuery.class))).andReturn(accounts);
        mocksControl.replay();
        Map<String, Object> serialisationParameters = new HashMap<String, Object>();
        serialisationParameters.put("depth", 2);
        String json = objectMapper.serialise(instance, serialisationParameters);
        System.out.println("json = " + json);
        mocksControl.verify();
        JSONObject jsonObject = new JSONObject(json);
        assertEquals("Test User", jsonObject.getJSONObject("user").getString("name"));
        assertEquals("5678", jsonObject.getJSONArray("accounts").getJSONObject(0).getString("id"));
    }
    
    @Test
    public void testSerialiseJSONForDB() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockInstanceService);
        ObjectMapper objectMapper = new JsonDBInstanceMapper();
        OlogyTemplate template = new OlogyTemplate();
        template.setId("1234");
        template.setType("subscription");
        template.setAttributeTemplate("status", new PropertyTemplate());
        template.setAttributeTemplate("seq", new SequenceTemplate("seq1", 4));
        template.setAttributeTemplate("startTime", new TimeTemplate());
        template.setAttributeTemplate("limit", new MeasurementTemplate());
        template.setAttributeTemplate("ids", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("limits", new CollectionTemplate(new MeasurementTemplate()));
        template.setAttributeTemplate("account", new LinkedObjectTemplate("account", "subscription"));
        LinkedObjectsTemplate linkedUsersTemplate = new LinkedObjectsTemplate("user", "subscription");
        template.setAttributeTemplate("users", linkedUsersTemplate);
        OlogyTemplate partTemplate = new OlogyTemplate();
        partTemplate.setAttributeTemplate("permissions", new PropertyTemplate());
        partTemplate.setAttributeTemplate("user", new LinkTemplate());
        template.setAttributeTemplate("accountHolder", partTemplate);
        OlogyTemplate featureTemplate = new OlogyTemplate();
        featureTemplate.setAttributeTemplate("title", new PropertyTemplate());
        featureTemplate.setAttributeTemplate("name", new PropertyTemplate());
        template.setAttributeTemplate("features", new CollectionTemplate(featureTemplate));
        OlogyInstance object = new OlogyInstance();
        object.setTemplate(template);
        object.setAttribute("seq", new Property("0001"));
        object.setAttribute("status", new Property("Created"));
        object.setAttribute("startTime", new Time("01/01/2001 00:00:00"));
        object.setAttribute("limit", new Measurement("1000"));
        LinkedObject accountLink = new LinkedObject();
        accountLink.setTemplate(new LinkedObjectTemplate("account", "subscription"));
        object.setAttribute("account", accountLink);
        LinkedObjects linkedUsers = new LinkedObjects();
        linkedUsers.setTemplate(linkedUsersTemplate);
        object.setAttribute("users", linkedUsers);
        Collection limits = new Collection();
        List limitsList = new ArrayList<Measurement>();
        limitsList.add(new Measurement("123"));
        limits.setMembers(limitsList);
        object.setAttribute("limits", limits);
        OlogyInstance partInstance = new OlogyInstance();
        partInstance.setTemplate(partTemplate);
        partInstance.setAttribute("permissions", new Property("all"));
        partInstance.setAttribute("user", new Link("1234"));
        object.setAttribute("accountHolder", partInstance);
        Collection features = new Collection();
        object.setAttribute("features", features);
        OlogyInstance feature = new OlogyInstance();
        feature.setTemplate(featureTemplate);
        feature.setAttribute("title", new Property("Feature 1"));
        features.add(feature);
        String json = objectMapper.serialise(object);
        System.out.println("json = " + json);
        JSONObject jsonObject = new JSONObject(json);
        assertEquals("1234", jsonObject.get("template"));
        assertEquals("Created", jsonObject.get("status"));
        assertEquals("0001", jsonObject.get("seq"));
        assertEquals("2001-01-01T00:00:00+0000", jsonObject.get("startTime"));
        assertEquals("1000", jsonObject.get("limit"));
        assertEquals("all", jsonObject.getJSONObject("accountHolder").getString("permissions"));
        assertEquals("1234", jsonObject.getJSONObject("accountHolder").getString("user"));
        assertEquals(1, jsonObject.getJSONArray("limits").length());
        assertEquals("123", jsonObject.getJSONArray("limits").get(0));
        assertEquals("Feature 1", jsonObject.getJSONArray("features").getJSONObject(0).getString("title"));
        assertEquals("{}", jsonObject.opt("account").toString());
        assertEquals("{}", jsonObject.opt("users").toString());
    }

}
