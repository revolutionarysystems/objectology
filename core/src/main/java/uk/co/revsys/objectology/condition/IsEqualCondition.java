package uk.co.revsys.objectology.condition;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class IsEqualCondition implements Condition {

    private String attribute;
    private Object value;

    public IsEqualCondition() {
    }

    public IsEqualCondition(String attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public void check(OlogyInstance instance) throws FailedConditionException {
        Attribute attribute = instance.getAttribute(getAttribute());
        if (attribute == null){
            if(getValue() != null){
                throw new FailedConditionException("Expected " + getValue() + ", found null");
            }
        }else if(!attribute.equals(getValue())){
            throw new FailedConditionException("Expected " + getValue() + ", found " + attribute);
        }
    }

    @Override
    public String getNature() {
        return "isEqual";
    }

}
