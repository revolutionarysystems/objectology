package uk.co.revsys.objectology.security;

import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class IsAdministratorConstraint implements SecurityConstraint{

    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        return false;
    }

    @Override
    public String getNature() {
        return "isAdministrator";
    }

}
