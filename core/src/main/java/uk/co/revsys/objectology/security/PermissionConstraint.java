package uk.co.revsys.objectology.security;

import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class PermissionConstraint implements SecurityConstraint{

    private String permission;

    public PermissionConstraint() {
    }

    public PermissionConstraint(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    @Override
    public String getNature() {
        return "permissionConstraint";
    }
    
}
