package uk.co.revsys.objectology.condition;

import uk.co.revsys.objectology.model.ObjectWithNature;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface Condition extends ObjectWithNature{

    public void check(OlogyInstance instance) throws FailedConditionException;
    
}
