package uk.co.revsys.objectology.condition;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;

public class IsEqualConditionTest {

    public IsEqualConditionTest() {
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
    public void testIsSatisfied() throws Exception {
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("p1", new PropertyTemplate());
        template.setAttributeTemplate("m1", new MeasurementTemplate());
        OlogyInstance instance = new OlogyInstance(template);
        instance.setAttribute("p1", new Property("v1"));
        instance.setAttribute("m1", new Measurement(1));
        IsEqualCondition condition = new IsEqualCondition("p1", "v1");
        condition.check(instance);
        condition = new IsEqualCondition("p1", "v2");
        try {
            condition.check(instance);
            fail("Expected FailedConditionException to be thrown");
        } catch (FailedConditionException ex) {
            // pass
        }
        condition = new IsEqualCondition("p2", "v1");
        try {
            condition.check(instance);
            fail("Expected FailedConditionException to be thrown");
        } catch (FailedConditionException ex) {
            // pass
        }
        condition = new IsEqualCondition("m1", 1);
        condition.check(instance);
        condition = new IsEqualCondition("m1", 2);
        try {
            condition.check(instance);
            fail("Expected FailedConditionException to be thrown");
        } catch (FailedConditionException ex) {
            // pass
        }
    }

}
