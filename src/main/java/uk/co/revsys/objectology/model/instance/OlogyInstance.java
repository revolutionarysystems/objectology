package uk.co.revsys.objectology.model.instance;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.annotation.Transient;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.OlogyObject;

public class OlogyInstance implements OlogyObject,Attribute<OlogyTemplate>{

	private String id;
	private String name;
	private OlogyTemplate template;
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

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
