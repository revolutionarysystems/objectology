package uk.co.revsys.objectology.model.instance;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.OlogyObject;

public class OlogyInstance extends OlogyObject implements Attribute<OlogyTemplate>{

	private OlogyTemplate template;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	@Override
	public OlogyTemplate getTemplate() {
		return template;
	}

	@Override
	public void setTemplate(OlogyTemplate template) {
		this.template = template;
	}
	
	public String getType(){
		return getTemplate().getType();
	}

	@Override
	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void setAttribute(String key, Attribute attribute) {
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
	
	public Map<String, Attribute> getAllAttributes(){
		Map<String, Attribute> combinedAttributes = new HashMap<String, Attribute>();
		combinedAttributes.putAll(getTemplate().getAttributes());
		combinedAttributes.putAll(getAttributes());
		return combinedAttributes;
	}

	
	
}
