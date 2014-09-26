package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.PersistedObject;

public interface PersistedObjectService<O extends PersistedObject>{
	
	public O create(O object) throws DaoException;
	
	public O update(O object) throws DaoException;
	
	public void delete(O object) throws DaoException;
	
}
