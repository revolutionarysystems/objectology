
package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParser;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
        String json = "{	\"name\": \"summary\",	\"templates\": {		\"$\": {			\"uid\": \"$.id\",			\"description\": \"$.template.type + ':' + $.name\",			\"partUid\": \"$.attributes.part.id\",			\"part\": \"$.attributes.part\",			\"strings\": \"$.attributes.properties.members\"		},		\"$.attributes.part\": {			\"id\": \"$.id\",		},		\"$.attributes.properties.members\": \"$.value\"	}}";
        ViewDefinitionParserImpl parser = new ViewDefinitionParserImpl();
        ViewDefinition result = parser.parse(json);
        assertEquals("summary", result.getName());
        assertEquals("$", result.getRoot());
    }
    
    public void testParseAndTransform() throws Exception{
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
        String json = "{	\"name\": \"summary\",	\"templates\": {		\"$\": {			\"uid\": \"$.id\",			\"description\": \"$.template.type + ':' + $.name\",			\"partUid\": \"$.attributes.part.id\",			\"part\": \"$.attributes.part\",			\"strings\": \"$.attributes.properties.members\"		},		\"$.attributes.part\": {			\"id\": \"$.id\",		},		\"$.attributes.properties.members\": \"$.value\"	}}";
        ViewDefinitionParser parser = new ViewDefinitionParserImpl();
        ViewDefinition transform = parser.parse(json);
        System.out.println(new ObjectMapper().writeValueAsString(transform));
        OlogyTransformer transformer = new OlogyTransformerImpl();
        OlogyView result = (OlogyView) transformer.transform(instance, transform);
        assertEquals("1234", result.get("uid"));
        assertEquals("5678", result.get("partUid"));
        assertEquals("5678", ((OlogyView)result.get("part")).get("id"));
        assertEquals("abc", ((List)result.get("strings")).get(0));
    }

}