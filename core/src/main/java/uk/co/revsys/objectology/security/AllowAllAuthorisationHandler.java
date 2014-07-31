package uk.co.revsys.objectology.security;

import java.util.List;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class AllowAllAuthorisationHandler implements AuthorisationHandler{

    @Override
    public boolean isAdministrator() {
        return true;
    }

    @Override
    public boolean isAuthorised(OlogyInstance instance, List<SecurityConstraint> securityConstraints) {
        return true;
    }

}
