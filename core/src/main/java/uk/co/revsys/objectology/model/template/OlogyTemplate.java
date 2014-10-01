package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.mapping.json.deserialise.AttributeTemplatesDeserialiser;
import uk.co.revsys.objectology.mapping.xml.XMLRootElement;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.security.SecurityConstraint;
import uk.co.revsys.objectology.view.View;

//@JsonDeserialize(using = TemplateDeserialiser.class)
@XMLRootElement(field = "type")
public class OlogyTemplate extends AbstractAttributeTemplate<OlogyInstance> implements PersistedObject{
    
    private String id;
    private String name;
    
	private String type;
	private Map<String, AttributeTemplate> attributeTemplates = new HashMap<String, AttributeTemplate>();
    private List<SecurityConstraint> creationConstraints = new ArrayList<SecurityConstraint>();
    private Map<String, Action> actions = new HashMap<String, Action>();
    private Map<String, View> views = new HashMap<String, View>();

    public OlogyTemplate() {
        views.put("default", new View("default"));
        views.put("identifier", new View("identifier"));
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    @JsonProperty("attributes")
	public Map<String, AttributeTemplate> getAttributeTemplates() {
		return Collections.unmodifiableMap(attributeTemplates);
	}

    @JsonProperty("attributes")
    @JsonDeserialize(using = AttributeTemplatesDeserialiser.class)
	public void setAttributeTemplates(Map<String, AttributeTemplate> attributeTemplates) {
		this.attributeTemplates = attributeTemplates;
	}
    
    public void setAttributeTemplate(String key, AttributeTemplate template){
        attributeTemplates.put(key, template);
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
            views.put("default", new View("default"));
        }
        if(!views.containsKey("identifier")){
            views.put("identifier", new View("identifier"));
        }
        this.views = views;
    }

    @Override
    public String getNature() {
        return "object";
    }

    @Override
    public void validate(OlogyInstance attribute) throws ValidationException {
        
    }

    @Override
    public OlogyInstance createDefaultInstance() {
        return null;
    }

}
