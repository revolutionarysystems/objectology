package uk.co.revsys.objectology.dao;

import uk.co.revsys.objectology.model.PersistedObject;

public abstract class AbstractDao<O extends PersistedObject> implements Dao<O>{

	private final String objectType;

	public AbstractDao(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectType() {
		return objectType;
	}
	
}
