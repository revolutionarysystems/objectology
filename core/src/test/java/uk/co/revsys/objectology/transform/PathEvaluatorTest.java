
package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.transform.path.PathEvaluatorImpl;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.Measurement;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class PathEvaluatorTest {

    public PathEvaluatorTest() {
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
    public void testEvaluate() throws DaoException, PathEvaluatorException {
        IMocksControl mocksControl = EasyMock.createControl();
        OlogyInstanceService mockInstanceServce = mocksControl.createMock(OlogyInstanceService.class);
        OlogyObjectServiceFactory.setOlogyInstanceService(mockInstanceServce);
        OlogyTemplate template = new OlogyTemplate();
        template.setId("abcd");
        OlogyInstance instance = new OlogyInstance();
        instance.setTemplate(template);
        instance.setId("1234");
        instance.setAttribute("p1", new Property("v1"));
        OlogyInstance part = new OlogyInstance();
        part.setId("5678");
        part.setAttribute("m1", new Measurement(111));
        instance.setAttribute("part", part);
        LinkTemplate linkTemplate = new LinkTemplate("other", ReferenceType.id);
        Link link = new Link("9876");
        link.setTemplate(linkTemplate);
        instance.setAttribute("link", link);
        PathEvaluator pathEvaluator = new PathEvaluatorImpl();
        OlogyInstance associatedObject = new OlogyInstance();
        expect(mockInstanceServce.findById("other", "9876")).andReturn(associatedObject);
        mocksControl.replay();
        assertEquals("1234", pathEvaluator.evaluate(instance, "$.id"));
        assertEquals("abcd", pathEvaluator.evaluate(instance, "$.template.id"));
        assertEquals("v1", pathEvaluator.evaluate(instance, "$.attributes.p1.value"));
        assertEquals(part, pathEvaluator.evaluate(instance, "$.attributes.part"));
        assertEquals("5678", pathEvaluator.evaluate(instance, "$.attributes.part.id"));
        assertEquals(new Measurement(111), pathEvaluator.evaluate(instance, "$.attributes.part.attributes.m1"));
        assertEquals("9876", pathEvaluator.evaluate(instance, "$.attributes.link.reference"));
        assertEquals(associatedObject, pathEvaluator.evaluate(instance, "$.attributes.link.associatedObject"));
        assertEquals("abcd:1234", pathEvaluator.evaluate(instance, "$.template.id + ':' + $.id"));
        mocksControl.verify();
    }

}