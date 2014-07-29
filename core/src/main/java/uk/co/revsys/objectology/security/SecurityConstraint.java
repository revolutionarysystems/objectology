package uk.co.revsys.objectology.security;

import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface SecurityConstraint extends ObjectWithNature {

    public boolean isSatisfied(OlogyInstance instance);
    
}
