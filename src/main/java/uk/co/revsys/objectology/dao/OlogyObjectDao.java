package uk.co.revsys.objectology.dao;

import java.util.List;
import uk.co.revsys.objectology.model.OlogyObject;

public interface OlogyObjectDao<O extends OlogyObject> {

	public O create(O object) throws DaoException;
	
	public List<O> findAll() throws DaoException;
	
	public O findById(String id) throws DaoException;
	
	public O update(O object) throws DaoException;
	
	public void delete(O object) throws DaoException;
	
}
