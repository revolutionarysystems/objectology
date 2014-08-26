package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.mapping.json.deserialise.AttributeTemplatesDeserialiser;
import uk.co.revsys.objectology.mapping.xml.XMLRootElement;
import uk.co.revsys.objectology.security.SecurityConstraint;
import uk.co.revsys.objectology.view.View;

//@JsonDeserialize(using = TemplateDeserialiser.class)
@XMLRootElement(field = "type")
public class OlogyTemplate extends OlogyObject implements AttributeTemplate<OlogyInstance>{
    
	private String type;
	private OlogyInstance value;
	private Map<String, AttributeTemplate> attributeTemplates = new HashMap<String, AttributeTemplate>();
    private List<SecurityConstraint> viewConstraints = new ArrayList<SecurityConstraint>();
    private List<SecurityConstraint> creationConstraints = new ArrayList<SecurityConstraint>();
    private Map<String, Action> actions = new HashMap<String, Action>();
    private Map<String, View> views = new HashMap<String, View>();

    public OlogyTemplate() {
        views.put("default", new View());
        views.put("identifier", new View());
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public OlogyInstance getValue() {
		return value;
	}

	@Override
	public void setValue(OlogyInstance value) {
		this.value = value;
	}
	
    @JsonProperty("attributes")
	public Map<String, AttributeTemplate> getAttributeTemplates() {
		return attributeTemplates;
	}

    @JsonProperty("attributes")
    @JsonDeserialize(using = AttributeTemplatesDeserialiser.class)
	public void setAttributeTemplates(Map<String, AttributeTemplate> attributeTemplates) {
		this.attributeTemplates = attributeTemplates;
	}
	
	public AttributeTemplate getAttributeTemplate(String key){
		return attributeTemplates.get(key);
	}
	
	public <T extends AttributeTemplate> T getAttributeTemplate(String key, Class<? extends T> type){
		return (T) attributeTemplates.get(key);
	}

	@Override
	public Class<? extends OlogyInstance> getAttributeType() {
		return OlogyInstance.class;
	}

	@Override
    @JsonIgnore
	public Map<String, Attribute> getAttributes() {
		Map<String, Attribute> attributes = new HashMap<String, Attribute>();
		for(Entry<String, AttributeTemplate> entry: getAttributeTemplates().entrySet()){
			attributes.put(entry.getKey(), entry.getValue().getValue());
		}
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, Attribute> attributes) {
		for(Entry<String, Attribute> attribute: attributes.entrySet()){
			getAttributeTemplate(attribute.getKey()).setValue(attribute.getValue());
		}
	}

	@Override
	public void setAttribute(String key, Attribute attribute) {
		getAttributeTemplate(key).setValue(attribute);
	}

	@Override
	public Attribute getAttribute(String key) {
		return getAttributeTemplate(key).getValue();
	}

	@Override
	public <A extends Attribute> A getAttribute(String key, Class<? extends A> type) {
		return (A) getAttribute(key);
	}

    public List<SecurityConstraint> getViewConstraints() {
        return viewConstraints;
    }

    public void setViewConstraints(List<SecurityConstraint> viewConstraints) {
        this.viewConstraints = viewConstraints;
    }

    public List<SecurityConstraint> getCreationConstraints() {
        return creationConstraints;
    }

    public void setCreationConstraints(List<SecurityConstraint> creationConstraints) {
        this.creationConstraints = creationConstraints;
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public void setActions(Map<String, Action> actions) {
        this.actions = actions;
    }

    public Map<String, View> getViews() {
        return views;
    }

    public void setViews(Map<String, View> views) {
        if(!views.containsKey("default")){
            views.put("default", new View());
        }
        if(!views.containsKey("identifier")){
            views.put("identifier", new View());
        }
        this.views = views;
    }

    @Override
    public String getNature() {
        return "object";
    }

}
