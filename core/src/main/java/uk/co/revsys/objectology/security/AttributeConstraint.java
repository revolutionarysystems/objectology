package uk.co.revsys.objectology.security;

import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class AttributeConstraint implements SecurityConstraint{

    private String attribute;
    private String value;

    public AttributeConstraint() {
    }

    public AttributeConstraint(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public boolean isSatisfied(OlogyInstance instance) {
        return instance.getAttribute(attribute).equals(value);
    }

    @Override
    public String getNature() {
        return "attributeConstraint";
    }

}
