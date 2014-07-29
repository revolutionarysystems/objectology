package uk.co.revsys.objectology.security;

import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.user.manager.model.User;

public class UserAttributeConstraint implements SecurityConstraint{

    private String attribute;
    private String value;

    public UserAttributeConstraint() {
    }

    public UserAttributeConstraint(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        User user = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
        return user.getAttributes().get(attribute).equals(value);
    }

    @Override
    public String getNature() {
        return "userAttributeConstraint";
    }

}
