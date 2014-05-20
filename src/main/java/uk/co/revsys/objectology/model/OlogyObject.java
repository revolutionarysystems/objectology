package uk.co.revsys.objectology.model;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.model.instance.Attribute;

public interface OlogyObject {

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public Map<String, Attribute> getAttributes();

	public void setAttributes(Map<String, Attribute> attributes);
	
	public void setAttribute(String key, Attribute attribute);
	
	public Attribute getAttribute(String key);
	
	public <A extends Attribute> A getAttribute(String key, Class<? extends A> type);
	
}
