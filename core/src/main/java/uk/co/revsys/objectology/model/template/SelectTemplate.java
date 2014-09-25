package uk.co.revsys.objectology.model.template;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Property;

public class SelectTemplate extends PropertyTemplate<Property>{

    private List<String> options = new LinkedList<String>();

    public SelectTemplate() {
    }

    public SelectTemplate(List<String> options) {
        this.options = options;
    }
    
    public SelectTemplate(String... options){
        this.options = new LinkedList<String>(Arrays.asList(options));
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @Override
    public void validate(Property attribute) throws ValidationException {
        if(!options.contains(attribute.getValue())){
            throw new ValidationException("Invalid value: " + attribute.getValue());
        }
    }
    
    @Override
    public Class<? extends Property> getAttributeType() {
        return Property.class;
    }

    @Override
    public String getNature() {
        return "select";
    }

}
