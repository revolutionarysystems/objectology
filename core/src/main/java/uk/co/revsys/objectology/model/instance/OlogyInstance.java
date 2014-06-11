package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.serialiser.jackson.OlogyInstanceDeserialiser;


@JsonDeserialize(using = OlogyInstanceDeserialiser.class)
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
    @JsonAnyGetter
    @JsonUnwrapped
	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(Map<String, Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
    @JsonAnySetter
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
	
    @JsonIgnore
	public Map<String, Attribute> getAllAttributes(){
		Map<String, Attribute> combinedAttributes = new HashMap<String, Attribute>();
		combinedAttributes.putAll(getTemplate().getAttributes());
		combinedAttributes.putAll(getAttributes());
		return combinedAttributes;
	}

	
	
}
