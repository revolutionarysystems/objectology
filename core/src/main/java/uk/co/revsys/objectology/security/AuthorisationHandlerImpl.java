package uk.co.revsys.objectology.security;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class AuthorisationHandlerImpl implements AuthorisationHandler {

    private final String administratorRole;
    private final String userRole;

    public AuthorisationHandlerImpl(String administratorRole, String userRole) {
        this.administratorRole = administratorRole;
        this.userRole = userRole;
    }

    @Override
    public boolean isAdministrator() {
        return SecurityUtils.getSubject().hasRole(administratorRole);
    }

    @Override
    public boolean isUser() {
        return SecurityUtils.getSubject().hasRole(userRole);
    }
    
    @Override
    public boolean isAuthorised(OlogyInstance instance, List<SecurityConstraint> securityConstraints) {
        if (isAdministrator()) {
            return true;
        } else if (!isUser()) {
            return false;
        } else {
            if (securityConstraints == null || securityConstraints.isEmpty()) {
                return true;
            }
            for (SecurityConstraint securityConstraint : securityConstraints) {
                boolean satisfied = securityConstraint.isSatisfied(instance);
                if (satisfied) {
                    return true;
                }
            }
            return false;
        }
    }

}
