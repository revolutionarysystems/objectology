package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.OlogyObjectDao;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public class OlogyTemplateServiceImpl<T extends OlogyTemplate> implements OlogyTemplateService<T>{

	private final OlogyObjectDao<T> dao;
	private final OlogyObjectValidator<T> validator;

	public OlogyTemplateServiceImpl(OlogyObjectDao<T> dao, OlogyObjectValidator<T> validator) {
		this.dao = dao;
		this.validator = validator;
	}

	@Override
	public List<T> findAll() throws DaoException{
		return dao.findAll();
	}

	@Override
	public T findById(String id) throws DaoException{
		return dao.findById(id);
	}

	@Override
	public T create(T object) throws DaoException{
		object = validator.validate(object);
		return dao.create(object);
	}

	@Override
	public T update(T object) throws DaoException{
		object = validator.validate(object);
		return dao.update(object);
	}

	@Override
	public void delete(T object) throws DaoException{
		dao.delete(object);
	}

	public OlogyObjectDao<T> getDao() {
		return dao;
	}
	
}
