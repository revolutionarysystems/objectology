
package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.rule.OneToOneMappingRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRuleSet;
import uk.co.revsys.objectology.view.definition.rule.DelegateRule;
import uk.co.revsys.objectology.view.definition.rule.ReplaceRootRule;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.DictionaryTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;

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
    public void testTransform() throws Exception {
        OlogyTemplate template = new OlogyTemplate();
        template.setType("Instance");
        OlogyTemplate partTemplate = new OlogyTemplate();
        template.setAttributeTemplate("part", partTemplate);
        CollectionTemplate propertiesTemplate = new CollectionTemplate(new PropertyTemplate());
        template.setAttributeTemplate("properties", propertiesTemplate);
        template.setAttributeTemplate("dictionary", new DictionaryTemplate(new PropertyTemplate()));
        OlogyInstance instance = new OlogyInstance();
        instance.setId("1234");
        instance.setName("Test");
        instance.setTemplate(template);
        OlogyInstance part = new OlogyInstance();
        part.setId("5678");
        instance.setAttribute("part", part);
        Collection collection = new Collection();
        collection.add(new Property("abc"));
        collection.add(new Property("def"));
        instance.setAttribute("properties", collection);
        Dictionary dictionary = new Dictionary();
        dictionary.put("key1", new Property("value1"));
        instance.setAttribute("dictionary", dictionary);
        ViewDefinitionRuleSet rootRuleSet1 = new ViewDefinitionRuleSet();
        rootRuleSet1.addRule(new OneToOneMappingRule("uid", "$.id"));
        rootRuleSet1.addRule(new OneToOneMappingRule("description", "$.template.type + ':' + $.name"));
        rootRuleSet1.addRule(new OneToOneMappingRule("partUid", "$.attributes.part.id"));
        rootRuleSet1.addRule(new DelegateRule("part", "$.attributes.part", new OneToOneMappingRule("id", "$.id")));
        rootRuleSet1.addRule(new DelegateRule("strings", "$.attributes.properties.members", new ReplaceRootRule("$.value")));
        rootRuleSet1.addRule(new DelegateRule("dictionary", "$.attributes.dictionary.map", new ViewDefinitionRuleSet(new OneToOneMappingRule("key", "$.key"), new OneToOneMappingRule("value", "$.value"))));
        ViewDefinition transform1 = new ViewDefinition("Test", "$", rootRuleSet1);
        System.out.println(new ObjectMapper().writeValueAsString(transform1));
        OlogyTransformer transformer = new OlogyTransformerImpl();
        OlogyView result = (OlogyView) transformer.transform(instance, transform1);
        assertEquals("1234", result.get("uid"));
        assertEquals("5678", result.get("partUid"));
        assertEquals("5678", ((OlogyView)result.get("part")).get("id"));
        assertEquals("abc", ((List)result.get("strings")).get(0));
        assertEquals("key1", ((OlogyView)((List)result.get("dictionary")).get(0)).get("key"));
        assertEquals(new Property("value1"), ((OlogyView)((List)result.get("dictionary")).get(0)).get("value"));
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