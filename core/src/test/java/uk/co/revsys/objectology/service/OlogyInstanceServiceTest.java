package uk.co.revsys.objectology.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.InMemoryOlogyObjectDaoFactory;
import uk.co.revsys.objectology.dao.OlogyObjectDaoFactory;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;

public class OlogyInstanceServiceTest {

    public OlogyInstanceServiceTest() {
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
    public void test() throws DaoException, UnexpectedAttributeException, ValidationException {
        OlogyObjectDaoFactory instanceDaoFactory = new InMemoryOlogyObjectDaoFactory();
        OlogyInstanceServiceImpl<OlogyInstance> instanceService = new OlogyInstanceServiceImpl<OlogyInstance>(instanceDaoFactory, new OlogyInstanceValidator<OlogyInstance>());
        OlogyTemplate template1 = new OlogyTemplate();
        template1.setAttributeTemplate("property1", new PropertyTemplate());
        OlogyInstance instance1 = new OlogyInstance();
        instance1.setTemplate(template1);
        instance1.setAttribute("property1", new Property("value1"));
        try {
            instance1.setAttribute("property2", new Property("value2"));
            fail("Expected UnexpectedAttributeException to be thrown");
        } catch (UnexpectedAttributeException ex) {
            // pass
        }
        OlogyInstance result1 = instanceService.create(instance1);
        assertNotNull(result1);
        String instance1Id = result1.getId();
        assertNotNull(instance1Id);
        assertEquals("value1", result1.getAttribute("property1", Property.class).getValue());
        assertNull(result1.getAttribute("property2"));
    }

}
