package uk.co.revsys.objectology.action.model;

import java.util.List;
import uk.co.revsys.objectology.action.handler.ActionInvocationException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.security.SecurityConstraint;

public interface Action extends ObjectWithNature{

    public List<SecurityConstraint> getSecurityConstraints();
    
}
