package uk.co.revsys.objectology.model.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class OlogyTemplate extends OlogyObject implements AttributeTemplate<OlogyInstance>{

	private String type;
	private OlogyInstance value;
	private Map<String, AttributeTemplate> attributeTemplates = new HashMap<String, AttributeTemplate>();	

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
	public Class<? extends OlogyInstance> getAttributeType() {
		return OlogyInstance.class;
	}

	@Override
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

}
