package uk.co.revsys.objectology.security;

import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.user.manager.model.User;

public class AttributeMatchConstraint implements SecurityConstraint{

    private String attribute;
    private String userAttribute;
    
    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        User user = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
        return instance.getAttribute(attribute).equals(user.getAttributes().get(userAttribute));
    }

    @Override
    public String getNature() {
        return "attributeMatchConstraint";
    }

}
