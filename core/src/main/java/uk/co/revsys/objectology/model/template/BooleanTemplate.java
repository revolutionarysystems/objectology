package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.BooleanValue;

public class BooleanTemplate extends AtomicAttributeTemplate<BooleanValue>{

    @Override
    public Class<? extends BooleanValue> getAttributeType() {
        return BooleanValue.class;
    }

    @Override
    public String getNature() {
        return "boolean";
    }

}
