package uk.co.revsys.objectology.security;

import org.apache.shiro.SecurityUtils;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.user.manager.Constants;

public class IsAccountHolderConstraint extends IsAccountMemberConstraint{

    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        if(!super.isSatisfied(instance)){
            return false;
        }
        return SecurityUtils.getSubject().hasRole(Constants.ACCOUNT_OWNER_ROLE);
    }
    
    @Override
    public String getNature() {
        return "isAccountHolder";
    }

}
