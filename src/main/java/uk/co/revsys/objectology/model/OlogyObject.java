package uk.co.revsys.objectology.model;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.instance.Attribute;

public class OlogyObject {

	private String id;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public void setAttribute(String key, Attribute attribute){
		attributes.put(key, attribute);
	}
	
	public Attribute getAttribute(String key){
		return attributes.get(key);
	}
	
	public <A extends Attribute> A getAttribute(String key, Class<? extends A> type){
		return (A)attributes.get(key);
	}
	
}
