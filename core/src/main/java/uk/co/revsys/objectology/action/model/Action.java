package uk.co.revsys.objectology.action.model;

import java.util.List;
import uk.co.revsys.objectology.condition.Condition;
import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.security.SecurityConstraint;

public interface Action extends ObjectWithNature{

    public List<SecurityConstraint> getSecurityConstraints();
    
    public List<Condition> getConditions();
    
}
