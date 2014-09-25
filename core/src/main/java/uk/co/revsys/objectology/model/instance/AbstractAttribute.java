package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.revsys.objectology.exception.NoTemplateException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public abstract class AbstractAttribute<T extends AttributeTemplate> implements Attribute<T> {

    private T template;
    private OlogyInstance parent;

    public AbstractAttribute() {
    }

    @Override
    public void setTemplate(T template) throws ValidationException {
        this.template = template;
        validate();
    }

    @Override
    @JsonIgnore
    public T getTemplate() {
        if (template == null) {
            throw new NoTemplateException("No template has been attached to this instance");
        }
        return template;
    }

    @Override
    @JsonIgnore
    public OlogyInstance getParent() {
        return parent;
    }

    @Override
    public void setParent(OlogyInstance parent) {
        this.parent = parent;
    }

    @Override
    public void validate() throws ValidationException {
        if (template != null) {
            template.validate(this);
        }
    }

}
