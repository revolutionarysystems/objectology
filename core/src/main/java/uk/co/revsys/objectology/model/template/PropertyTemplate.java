package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.regex.Pattern;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Property;

public class PropertyTemplate extends AtomicAttributeTemplate<Property>{

    private String constraint;
    @JsonIgnore
    private Pattern pattern;

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
        this.pattern = Pattern.compile(constraint);
    }

    @Override
    public void doValidate(Property attribute) throws ValidationException {
        if(attribute!=null && attribute.getValue()!=null && pattern!=null && !pattern.matcher(attribute.getValue()).matches()){
            throw new ValidationException("Expected " + constraint);
        }
    }
    
    @Override
    public Class<? extends Property> getAttributeType() {
        return (Class<? extends Property>) Property.class;
    }

    @Override
    public String getNature() {
        return "property";
    }

}
