package uk.co.revsys.objectology.dao;

import uk.co.revsys.objectology.model.OlogyObject;

public abstract class AbstractOlogyObjectDao<O extends OlogyObject> implements OlogyObjectDao<O>{

	private final String objectType;

	public AbstractOlogyObjectDao(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectType() {
		return objectType;
	}
	
}
