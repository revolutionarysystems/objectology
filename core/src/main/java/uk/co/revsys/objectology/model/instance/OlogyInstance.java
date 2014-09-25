package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.exception.NoTemplateException;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.mapping.json.deserialise.OlogyInstanceDeserialiser;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

@JsonDeserialize(using = OlogyInstanceDeserialiser.class)
public class OlogyInstance extends OlogyObject implements Attribute<OlogyTemplate> {

    private OlogyTemplate template;
    private OlogyInstance parent;
    private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

    public OlogyInstance() {
    }

    public OlogyInstance(OlogyTemplate template) {
        this.template = template;
    }

    @Override
    public OlogyTemplate getTemplate() {
        if(template==null){
            throw new NoTemplateException("No template has been attached to this instance");
        }
        return template;
    }

    @Override
    public void setTemplate(OlogyTemplate template) {
        this.template = template;
    }

    @Override
    public void setParent(OlogyInstance parent) {
        this.parent = parent;
    }

    @Override
    @JsonIgnore
    public OlogyInstance getParent() {
        return parent;
    }

    public String getType() {
        return getTemplate().getType();
    }

    @Override
    @JsonAnyGetter
    @JsonUnwrapped
    public Map<String, Attribute> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public void setAttributes(Map<String, Attribute> attributes) throws UnexpectedAttributeException, ValidationException {
        for (Entry<String, Attribute> entry : attributes.entrySet()) {
            setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    @JsonAnySetter
    public void setAttribute(String key, Attribute attribute) throws UnexpectedAttributeException, ValidationException {
        if (attribute != null) {
            attribute.setParent(this);
            AttributeTemplate template = getTemplate().getAttributeTemplate(key);
            if(template == null){
                throw new UnexpectedAttributeException(key);
            }
            attribute.setTemplate(template);
        }
        attributes.put(key, attribute);
    }

    @Override
    public Attribute getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public <A extends Attribute> A getAttribute(String key, Class<? extends A> type) {
        return (A) getAttribute(key);
    }

    @JsonIgnore
    public Map<String, Attribute> getAllAttributes() {
        Map<String, Attribute> combinedAttributes = new HashMap<String, Attribute>();
        combinedAttributes.putAll(getTemplate().getAttributes());
        combinedAttributes.putAll(getAttributes());
        return combinedAttributes;
    }

    @Override
    public void validate() throws ValidationException {
        template.validate(this);
    }

}
