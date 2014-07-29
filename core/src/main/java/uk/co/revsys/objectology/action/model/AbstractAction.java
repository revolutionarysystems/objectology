package uk.co.revsys.objectology.action.model;

import uk.co.revsys.objectology.action.handler.MissingParameterException;
import uk.co.revsys.objectology.action.model.Action;
import java.util.ArrayList;
import java.util.List;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.security.SecurityConstraint;

public abstract class AbstractAction implements Action{

    private List<SecurityConstraint> securityConstraints = new ArrayList<SecurityConstraint>();

    public AbstractAction() {
    }

    public AbstractAction(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }
    
    @Override
    public List<SecurityConstraint> getSecurityConstraints() {
        return securityConstraints;
    }

    public void setSecurityConstraints(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }
    
}
