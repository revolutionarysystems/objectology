
package uk.co.revsys.objectology.model.instance;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.ReferenceType;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class LinkTest {

	private IMocksControl mocksControl;
	private OlogyInstanceService mockOlogyInstanceService;
	
    public LinkTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
		mocksControl = EasyMock.createControl();
		mockOlogyInstanceService = mocksControl.createMock(OlogyInstanceService.class);
		OlogyObjectServiceFactory.setOlogyInstanceService(mockOlogyInstanceService);
    }

    @After
    public void tearDown() {
    }

	@Test
	public void testGetAssociatedObjectById() throws Exception {
		LinkTemplate linkTemplate = new LinkTemplate("user");
		Link link = new Link();
		link.setTemplate(linkTemplate);
		link.setReference("1234");
		OlogyInstance instance1 = new OlogyInstance();
		expect(mockOlogyInstanceService.findById("user", "1234")).andReturn(instance1);
		mocksControl.replay();
		OlogyObject result = link.getAssociatedObject();
		assertEquals(instance1, result);
		mocksControl.verify();
	}
	
	@Test
	public void testGetAssociatedObjectByName() throws Exception {
		LinkTemplate linkTemplate = new LinkTemplate("user");
		linkTemplate.setReferenceType(ReferenceType.name);
		Link link = new Link();
		link.setTemplate(linkTemplate);
		link.setReference("Fred");
		OlogyInstance instance1 = new OlogyInstance();
		expect(mockOlogyInstanceService.findByName("user", "Fred")).andReturn(instance1);
		mocksControl.replay();
		OlogyObject result = link.getAssociatedObject();
		assertEquals(instance1, result);
		mocksControl.verify();
	}

}