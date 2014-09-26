package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.dao.SequenceException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.service.ServiceFactory;

public class SequenceTemplate extends PropertyTemplate{

    private String name;
    private int length;

    public SequenceTemplate() {
    }

    public SequenceTemplate(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void validate(Property sequence) throws ValidationException {
        if(sequence.getValue() == null || sequence.getValue().isEmpty()){
            try {
                sequence.setValue(ServiceFactory.getSequenceGenerator().getNextSequence(getName(), getLength()));
            } catch (SequenceException ex) {
                throw new ValidationException(ex);
            }
        }
    }
    
    @Override
    public Class<? extends Property> getAttributeType() {
        return Property.class;
    }

    @Override
    public String getNature() {
        return "sequence";
    }

}
