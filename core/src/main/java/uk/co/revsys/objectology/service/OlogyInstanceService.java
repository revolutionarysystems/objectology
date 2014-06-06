package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.Query;

public interface OlogyInstanceService<I extends OlogyInstance> extends OlogyObjectService<I> {

	public List<I> findAll(String type) throws DaoException;
	
	public <V extends Object> List<V> findAll(String type, Class<? extends V> view) throws DaoException;
	
	public List<I> find(String type, Query query) throws DaoException;
	
	public <V extends Object> List<V> find(String type, Query query, Class<? extends V> view) throws DaoException;

	public I findById(String type, String id) throws DaoException;

	public I findByName(String type, String name) throws DaoException;

}
