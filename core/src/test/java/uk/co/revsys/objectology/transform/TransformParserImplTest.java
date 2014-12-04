
package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParserImpl;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.view.definition.DefaultViewDefinition;
import uk.co.revsys.objectology.view.definition.IdentifierViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParser;
import uk.co.revsys.objectology.view.definition.rule.FilterRule;
import uk.co.revsys.objectology.view.definition.rule.TemplateRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class TransformParserImplTest {

    public TransformParserImplTest() {
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
    public void testParse() throws Exception {
        String json = "{\"name\": \"identifier\", \"root\": \"/\", \"rules\": { \"main\": {\"type\": \"template\", \"mappings\": {\"id\": \"id\"}}}}";
        ViewDefinitionParserImpl parser = new ViewDefinitionParserImpl();
        ViewDefinition result = parser.parse(json);
        assertEquals("identifier", result.getName());
        assertEquals("/", result.getRoot());
        Map<String, ViewDefinitionRule> rules = result.getRules();
        assertEquals(1, rules.values().size());
        assertTrue(rules.containsKey("main"));
        assertTrue(rules.get("main") instanceof TemplateRule);
        TemplateRule rule = (TemplateRule) rules.get("main");
    }
    
    @Test
    public void testParseAndTransform() throws Exception {
        OlogyTemplate template = new OlogyTemplate();
        template.setType("Instance");
        template.setAttributeTemplate("p1", new PropertyTemplate());
        template.setAttributeTemplate("p2", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance(template);
        instance.setId("1234");
        instance.setName("Test");
        instance.setAttribute("p1", new Property("v1"));
        instance.setAttribute("p2", new Property("v2"));
        instance.setTemplate(template);
        String json = "{\"name\": \"identifier\", \"root\": \"/\", \"rules\": { \"main\": {\"type\": \"chain\", \"rules\": [\"template\", \"flatten\", \"filter\"]}, \"template\": {\"type\": \"template\", \"mappings\": {\"id\": \"id\", \"attributes\": \"attributes\"}}, \"flatten\": {\"type\": \"flatten\", \"key\": \"attributes\"}, \"filter\": {\"type\": \"filter\", \"excludes\": [\"p2\"]}}}";
        ViewDefinitionParser parser = new ViewDefinitionParserImpl();
        ViewDefinition transform = parser.parse(json);
        OlogyTransformer transformer = new OlogyTransformerImpl();
        Map<String, Object> result = (Map<String, Object>) transformer.transform(instance, transform);
        System.out.println(new JsonInstanceMapper().serialise(result));
        assertEquals(2, result.values().size());
        assertEquals("1234", result.get("id"));
        assertEquals("v1", result.get("p1").toString());
        assertFalse(result.containsKey("p2"));
    }
    
    @Test
    public void testIdentifierView() throws Exception {
        OlogyTemplate template = new OlogyTemplate();
        template.setType("Instance");
        OlogyInstance instance = new OlogyInstance();
        instance.setId("1234");
        instance.setName("Test");
        instance.setTemplate(template);
        ViewDefinition transform = new IdentifierViewDefinition();
        OlogyTransformer transformer = new OlogyTransformerImpl();
        Map<String, Object> result = (Map<String, Object>) transformer.transform(instance, transform);
        assertEquals(1, result.values().size());
        assertEquals("1234", result.get("id"));
    }
    
    @Test
    public void testDefaultView() throws Exception {
        OlogyTemplate template = new OlogyTemplate();
        template.setType("Instance");
        OlogyInstance instance = new OlogyInstance();
        instance.setId("1234");
        instance.setName("Test");
        instance.setTemplate(template);
        ViewDefinition transform = new DefaultViewDefinition();
        OlogyTransformer transformer = new OlogyTransformerImpl();
        OlogyInstance result = (OlogyInstance) transformer.transform(instance, transform);
        assertEquals("1234", result.getId());
    }

}