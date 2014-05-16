package uk.co.revsys.objectology.service;

import uk.co.revsys.objectology.model.OlogyObject;

public interface OlogyObjectValidator<O extends OlogyObject> {
	
	public O validate(O object);
	
}
