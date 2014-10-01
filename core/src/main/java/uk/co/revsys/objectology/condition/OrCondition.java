package uk.co.revsys.objectology.condition;

import java.util.ArrayList;
import java.util.List;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class OrCondition implements Condition {

    private List<Condition> conditions;

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public void check(OlogyInstance instance) throws FailedConditionException {
        List<String> failures = new ArrayList<String>();
        for (Condition condition : conditions) {
            try {
                condition.check(instance);
                return;
            } catch (FailedConditionException ex) {
                failures.add(ex.getMessage());
            }
        }
        if(!failures.isEmpty()){
            throw new FailedConditionException("OR condition failed: " + failures.toString());
        }
    }

    @Override
    public String getNature() {
        return "or";
    }

}
