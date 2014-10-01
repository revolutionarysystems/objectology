package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.mapping.json.deserialise.OlogyInstanceDeserialiser;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

@JsonDeserialize(using = OlogyInstanceDeserialiser.class)
public class OlogyInstance extends AbstractAttribute<OlogyInstance, OlogyTemplate> implements PersistedObject{

    private String id;
    private String name;
    
    private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

    public OlogyInstance() {
    }

    public OlogyInstance(OlogyTemplate template) throws ValidationException {
        setTemplate(template);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore(false)
    public OlogyTemplate getTemplate() {
        return super.getTemplate();
    }

    @Override
    @JsonIgnore(false)
    public void setTemplate(OlogyTemplate template) throws ValidationException {
        super.setTemplate(template);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return getTemplate().getType();
    }

    @JsonAnyGetter
    @JsonUnwrapped
    public Map<String, Attribute> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void setAttributes(Map<String, Attribute> attributes) throws UnexpectedAttributeException, ValidationException {
        for (Entry<String, Attribute> entry : attributes.entrySet()) {
            setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @JsonAnySetter
    public void setAttribute(String key, Attribute attribute) throws UnexpectedAttributeException, ValidationException {
        if (attribute != null) {
            attribute.setParent(this);
            AttributeTemplate template = getTemplate().getAttributeTemplate(key);
            if(template == null || template.isStatic()){
                throw new UnexpectedAttributeException("Unexpected Attribute: " + key);
            }
            attribute.setTemplate(template);
        }
        attributes.put(key, attribute);
    }

    public Attribute getAttribute(String key) {
        return attributes.get(key);
    }

    public <A extends Attribute> A getAttribute(String key, Class<? extends A> type) {
        A attribute = (A) getAttribute(key);
        return attribute;
    }

}
