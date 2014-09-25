
package uk.co.revsys.objectology.model.instance;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.SelectTemplate;

public class SelectTest {

    public SelectTest() {
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
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("select", new SelectTemplate("opt1", "opt2"));
        OlogyInstance instance = new OlogyInstance(template);
        instance.setAttribute("select", new Property("opt1"));
        assertEquals("opt1", instance.getAttribute("select", Property.class).getValue());
        instance.setAttribute("select", new Property("opt2"));
        assertEquals("opt2", instance.getAttribute("select", Property.class).getValue());
        try{
            instance.setAttribute("select", new Property("opt3"));
            fail("Expected ValidationException to be thrown");
        }catch(ValidationException ex){
            //pass
        }
        try{
            instance.getAttribute("select", Property.class).setValue("opt3");
            fail("Expected ValidationException to be thrown");
        }catch(ValidationException ex){
            //pass
        }
    }

}