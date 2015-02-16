
package uk.co.revsys.objectology.security;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.service.OlogyInstanceService;
import uk.co.revsys.objectology.service.ServiceFactory;
import uk.co.revsys.user.manager.model.User;
import uk.co.revsys.user.manager.test.util.AbstractShiroTest;

public class IsAccountMemberConstraintTest extends AbstractShiroTest{

    public IsAccountMemberConstraintTest() {
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
        IMocksControl mocksControl = EasyMock.createControl();
        Subject mockSubject = mocksControl.createMock(Subject.class);
        setSubject(mockSubject);
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("account", new PropertyTemplate());
        OlogyInstance instance = new OlogyInstance(template);
        instance.setAttribute("account", new Property("1234"));
        User user = new User();
        user.setAccount("1234");
        PrincipalCollection principalCollection = new SimplePrincipalCollection(user, "test");
        expect(mockSubject.getPrincipals()).andReturn(principalCollection);
        mocksControl.replay();
        IsAccountMemberConstraint constraint = new IsAccountMemberConstraint();
        assertTrue(constraint.isSatisfied(instance));
        mocksControl.verify();
    }
    
    @Test
    public void testIsSatisfiedWithPath() throws Exception {
        IMocksControl mocksControl = EasyMock.createControl();
        Subject mockSubject = mocksControl.createMock(Subject.class);
        setSubject(mockSubject);
        OlogyInstanceService mockInstanceService = mocksControl.createMock(OlogyInstanceService.class);
        ServiceFactory.setOlogyInstanceService(mockInstanceService);
        OlogyTemplate associatedTemplate = new OlogyTemplate();
        associatedTemplate.setType("associatedInstance");
        associatedTemplate.setAttributeTemplate("account", new PropertyTemplate());
        OlogyInstance associatedInstance = new OlogyInstance(associatedTemplate);
        associatedInstance.setAttribute("account", new Property("1234"));
        OlogyTemplate template = new OlogyTemplate();
        template.setAttributeTemplate("linked", new LinkTemplate("associatedInstance"));
        OlogyInstance instance = new OlogyInstance(template);
        instance.setAttribute("linked", new Link("abcd"));
        User user = new User();
        user.setAccount("1234");
        PrincipalCollection principalCollection = new SimplePrincipalCollection(user, "test");
        expect(mockSubject.getPrincipals()).andReturn(principalCollection);
        expect(mockInstanceService.findById("associatedInstance", "abcd")).andReturn(associatedInstance);
        mocksControl.replay();
        IsAccountMemberConstraint constraint = new IsAccountMemberConstraint();
        constraint.setAttribute("linked/associatedObject/attributes/account");
        assertTrue(constraint.isSatisfied(instance));
        mocksControl.verify();
    }

}