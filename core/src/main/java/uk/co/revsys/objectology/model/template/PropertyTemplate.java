package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Property;

public class PropertyTemplate<P extends Property> extends AtomicAttributeTemplate<P>{

    @Override
    public Class<? extends P> getAttributeType() {
        return (Class<? extends P>) Property.class;
    }

	

}
