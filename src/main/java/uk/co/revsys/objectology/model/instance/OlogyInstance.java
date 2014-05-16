package uk.co.revsys.objectology.model.instance;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.model.OlogyObject;

public class OlogyInstance extends OlogyObject implements Attribute{

	private OlogyTemplate template;

	public OlogyTemplate getTemplate() {
		return template;
	}

	public void setTemplate(OlogyTemplate template) {
		this.template = template;
	}
	
	public String getType(){
		return getTemplate().getType();
	}
	
	public Map<String, Attribute> getAllAttributes(){
		Map<String, Attribute> combinedAttributes = new HashMap<String, Attribute>();
		combinedAttributes.putAll(getAttributes());
		combinedAttributes.putAll(getTemplate().getAttributes());
		return combinedAttributes;
	}

	@Override
	public Attribute getAttribute(String key) {
		return getAllAttributes().get(key);
	}
	
}
