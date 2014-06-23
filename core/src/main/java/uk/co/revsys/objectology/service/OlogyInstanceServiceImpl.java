package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.OlogyObjectDao;
import uk.co.revsys.objectology.dao.OlogyObjectDaoFactory;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.query.Query;

public class OlogyInstanceServiceImpl<I extends OlogyInstance> implements OlogyInstanceService<I>{

	private final OlogyObjectDaoFactory daoFactory;
	private final OlogyObjectValidator<I> validator;

	public OlogyInstanceServiceImpl(OlogyObjectDaoFactory daoFactory, OlogyObjectValidator<I> validator) {
		this.daoFactory = daoFactory;
		this.validator = validator;
	}

	@Override
	public List<I> findAll(String type) throws DaoException{
		return getDao(type).findAll();
	}

	@Override
	public <V> List<V> findAll(String type, Class<? extends V> view) throws DaoException {
		return getDao(type).findAll(view);
	}

	@Override
	public List<I> find(String type, Query query) throws DaoException {
		return getDao(type).find(query);
	}

	@Override
	public <V> List<V> find(String type, Query query, Class<? extends V> view) throws DaoException {
		return getDao(type).find(query, view);
	}

	@Override
	public I findById(String type, String id) throws DaoException{
		return getDao(type).findById(id);
	}

	@Override
	public I findByName(String type, String name) throws DaoException {
		return getDao(type).findByName(name);
	}

	@Override
	public I create(I object) throws DaoException{
		object = validator.validate(object);
		return getDao(object.getType()).create(object);
	}

	@Override
	public I update(I object) throws DaoException{
		object = validator.validate(object);
		return getDao(object.getType()).update(object);
	}

	@Override
	public void delete(I object) throws DaoException{
		getDao(object.getType()).delete(object);
	}
	
	public OlogyObjectDao<I> getDao(String type){
		return daoFactory.getDao(type);
	}

}