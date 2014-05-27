package uk.co.revsys.objectology.dao;

import java.util.List;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.query.Query;

public interface OlogyObjectDao<O extends OlogyObject> {

	public O create(O object) throws DaoException;

	public List<O> findAll() throws DaoException;
	
	public <V extends Object> List<V> findAll(Class<? extends V> view) throws DaoException;
	
	public List<O> find(Query query) throws DaoException;
	
	public <V extends Object> List<V> find(Query query, Class<? extends V> view) throws DaoException;

	public O findById(String id) throws DaoException;

	public O findByName(String name) throws DaoException;

	public O update(O object) throws DaoException;

	public void delete(O object) throws DaoException;

}
