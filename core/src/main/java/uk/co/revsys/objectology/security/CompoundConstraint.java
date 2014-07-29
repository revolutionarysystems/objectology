package uk.co.revsys.objectology.security;

import java.util.ArrayList;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class CompoundConstraint extends ArrayList<SecurityConstraint> implements SecurityConstraint{
    
    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        for(SecurityConstraint constraint: this){
            if(!constraint.isSatisfied(instance)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getNature() {
        return "compoundConstraint";
    }

}
