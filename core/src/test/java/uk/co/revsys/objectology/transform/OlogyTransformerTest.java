
package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.rule.OneToOneMappingRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRuleSet;
import uk.co.revsys.objectology.view.definition.rule.DelegateRule;
import uk.co.revsys.objectology.view.definition.rule.ReplaceRootRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.swing.text.html.ObjectView;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class OlogyTransformerTest {

    public OlogyTransformerTest() {
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
    public void testTransform() throws TransformException, JsonProcessingException {
        OlogyTemplate template = new OlogyTemplate();
        template.setType("Instance");
        OlogyInstance instance = new OlogyInstance();
        instance.setId("1234");
        instance.setName("Test");
        instance.setTemplate(template);
        OlogyInstance part = new OlogyInstance();
        part.setId("5678");
        instance.setAttribute("part", part);
        Collection collection = new Collection();
        collection.getMembers().add(new Property("abc"));
        collection.getMembers().add(new Property("def"));
        instance.setAttribute("properties", collection);
        ViewDefinitionRuleSet rootRuleSet1 = new ViewDefinitionRuleSet();
        rootRuleSet1.addRule(new OneToOneMappingRule("uid", "$.id"));
        rootRuleSet1.addRule(new OneToOneMappingRule("description", "$.template.type + ':' + $.name"));
        rootRuleSet1.addRule(new OneToOneMappingRule("partUid", "$.attributes.part.id"));
        rootRuleSet1.addRule(new DelegateRule("part", "$.attributes.part", new OneToOneMappingRule("id", "$.id")));
        rootRuleSet1.addRule(new DelegateRule("strings", "$.attributes.properties.members", new ReplaceRootRule("$.value")));
        ViewDefinition transform1 = new ViewDefinition("Test", "$", rootRuleSet1);
        System.out.println(new ObjectMapper().writeValueAsString(transform1));
        OlogyTransformer transformer = new OlogyTransformerImpl();
        OlogyView result = (OlogyView) transformer.transform(instance, transform1);
        assertEquals("1234", result.get("uid"));
        assertEquals("5678", result.get("partUid"));
        assertEquals("5678", ((OlogyView)result.get("part")).get("id"));
        assertEquals("abc", ((List)result.get("strings")).get(0));
        ViewDefinitionRuleSet rootRuleSet2 = new ViewDefinitionRuleSet();
        ViewDefinitionRuleSet identifierRueSet = new ViewDefinitionRuleSet();
        identifierRueSet.addRule(new OneToOneMappingRule("id", "$.id"));
        identifierRueSet.addRule(new OneToOneMappingRule("name", "$.name"));
        rootRuleSet2.addRule(new DelegateRule("identifier", "$", identifierRueSet));
        ViewDefinition transform2 = new ViewDefinition("Test", "$", rootRuleSet2);
        result = (OlogyView) transformer.transform(instance, transform2);
        assertEquals("1234", ((OlogyView)result.get("identifier")).get("id"));
        assertEquals("Test", ((OlogyView)result.get("identifier")).get("name"));
    }

}