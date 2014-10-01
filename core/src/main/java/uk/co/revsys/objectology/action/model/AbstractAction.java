package uk.co.revsys.objectology.action.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.condition.Condition;
import uk.co.revsys.objectology.security.SecurityConstraint;

public abstract class AbstractAction implements Action{

    private List<SecurityConstraint> securityConstraints = new ArrayList<SecurityConstraint>();
    private List<Condition> conditions = new LinkedList<Condition>();

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

    @Override
    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    public void addCondition(Condition condition){
        this.conditions.add(condition);
    }
    
}
