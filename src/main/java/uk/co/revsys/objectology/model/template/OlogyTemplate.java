package uk.co.revsys.objectology.model.template;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class OlogyTemplate extends OlogyObject implements IdentifiedAttributeTemplate{

	private String type;
	private Map<String, AttributeTemplate> attributeTemplates = new HashMap<String, AttributeTemplate>();	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, AttributeTemplate> getAttributeTemplates() {
		return attributeTemplates;
	}

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
	public Class<? extends Attribute> getAttributeType() {
		return OlogyInstance.class;
	}

}
