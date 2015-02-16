package uk.co.revsys.objectology.security;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.transform.path.JXPathEvaluator;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.user.manager.model.User;

public class IsAccountMemberConstraint implements SecurityConstraint{

    private PathEvaluator pathEvaluator = new JXPathEvaluator();
    
    private String attribute = "account";

    public IsAccountMemberConstraint() {
    }

    public IsAccountMemberConstraint(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        try {
            User user = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
            String accountId = user.getAccount();
            Property accountAttribute = (Property) pathEvaluator.evaluate(instance, "/attributes/" + getAttribute());
            return accountAttribute.equals(accountId);
        } catch (PathEvaluatorException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public String getNature() {
        return "isAccountMember";
    }

}
