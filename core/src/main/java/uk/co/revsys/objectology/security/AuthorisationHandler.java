package uk.co.revsys.objectology.security;

import java.util.List;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface AuthorisationHandler {

    public boolean isAdministrator();
    
    public boolean isAuthorised(OlogyInstance instance, List<SecurityConstraint> securityConstraints);
    
}
