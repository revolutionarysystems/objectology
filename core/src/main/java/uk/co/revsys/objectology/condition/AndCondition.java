package uk.co.revsys.objectology.condition;

import java.util.List;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class AndCondition implements Condition{

    private List<Condition> conditions;

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    @Override
    public void check(OlogyInstance instance) throws FailedConditionException {
        for(Condition condition: conditions){
            condition.check(instance);
        }
    }

    @Override
    public String getNature() {
        return "and";
    }

}
