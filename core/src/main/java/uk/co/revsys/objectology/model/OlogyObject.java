package uk.co.revsys.objectology.model;

import java.util.Map;
import uk.co.revsys.objectology.exception.UnexpectedAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Attribute;

public abstract class OlogyObject {

	private String id;
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public abstract Map<String, Attribute> getAttributes();

	public abstract void setAttributes(Map<String, Attribute> attributes) throws UnexpectedAttributeException, ValidationException;
	
	public abstract void setAttribute(String key, Attribute attribute) throws UnexpectedAttributeException, ValidationException;
	
	public abstract Attribute getAttribute(String key);
	
	public abstract <A extends Attribute> A getAttribute(String key, Class<? extends A> type);
	
}
