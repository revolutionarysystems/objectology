package uk.co.revsys.objectology.security;

import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class RoleConstraint implements SecurityConstraint{

    private String role;

    public RoleConstraint() {
    }

    public RoleConstraint(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    @Override
    public String getNature() {
        return "hasRole";
    }
    
}
