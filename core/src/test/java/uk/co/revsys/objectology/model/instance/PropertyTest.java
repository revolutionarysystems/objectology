package uk.co.revsys.objectology.model.instance;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.PropertyTemplate;

public class PropertyTest {

    public PropertyTest() {
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
    public void test() throws Exception {
        Property property1 = new Property("test");
        assertTrue(property1.equals("test"));
        assertEquals(new Property("test"), property1);
    }

    @Test
    public void testRegexConstraint() throws Exception {
        PropertyTemplate template = new PropertyTemplate();
        template.setConstraint("[A-Za-z]*");
        Property property = new Property();
        property.setValue("aBc");
        property.setTemplate(template);
        try {
            property.setValue("aBc1");
            fail("Expected ValidationException to be thrown");
        } catch (ValidationException ex) {
            // pass
        }
        property.setValue(null);
        property.setValue("");
    }

}
