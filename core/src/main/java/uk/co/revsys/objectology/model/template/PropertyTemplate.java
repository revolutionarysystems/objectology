package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Property;

public class PropertyTemplate extends AtomicAttributeTemplate<Property>{

    @Override
    public Class<? extends Property> getAttributeType() {
        return (Class<? extends Property>) Property.class;
    }

    @Override
    public String getNature() {
        return "property";
    }

	

}
