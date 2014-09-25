
package uk.co.revsys.objectology.model.instance;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.CollectionTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import static org.junit.Assert.*;

public class CollectionTest {

    public CollectionTest() {
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
    public void testRemove() throws ValidationException {
        CollectionTemplate template = new CollectionTemplate(new PropertyTemplate());
        List<Property> members = new ArrayList<Property>();
        members.add(new Property("m1"));
        members.add(new Property("m2"));
        members.add(new Property("m3"));
        Collection<Property> instance = new Collection<Property>();
        instance.setMembers(members);
        instance.setTemplate(template);
        assertEquals(3, instance.getMembers().size());
        assertTrue(instance.contains(new Property("m1")));
        assertTrue(instance.contains(new Property("m2")));
        assertTrue(instance.contains(new Property("m3")));
        instance.remove(new Property("m2"));
        assertEquals(2, instance.getMembers().size());
        assertTrue(instance.contains(new Property("m1")));
        assertFalse(instance.contains(new Property("m2")));
        assertTrue(instance.contains(new Property("m3")));
    }

}