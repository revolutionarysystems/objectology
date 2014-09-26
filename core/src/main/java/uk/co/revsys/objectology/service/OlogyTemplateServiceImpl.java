package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.Dao;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.query.Query;

public class OlogyTemplateServiceImpl<T extends OlogyTemplate> implements OlogyTemplateService<T>{

	private final Dao<T> dao;

	public OlogyTemplateServiceImpl(Dao<T> dao) {
		this.dao = dao;
	}

	@Override
	public List<T> findAll() throws DaoException{
		return dao.findAll();
	}

	@Override
	public <V> List<V> findAll(Class<? extends V> view) throws DaoException {
		return dao.findAll(view);
	}

	@Override
	public List<T> find(Query query) throws DaoException {
		return dao.find(query);
	}

	@Override
	public <V> List<V> find(Query query, Class<? extends V> view) throws DaoException {
		return dao.find(query, view);
	}

	@Override
	public T findById(String id) throws DaoException{
		return dao.findById(id);
	}

	@Override
	public T findByName(String name) throws DaoException{
		return dao.findByName(name);
	}

	@Override
	public T create(T object) throws DaoException{
		return dao.create(object);
	}

	@Override
	public T update(T object) throws DaoException{
		return dao.update(object);
	}

	@Override
	public void delete(T object) throws DaoException{
		dao.delete(object);
	}

	public Dao<T> getDao() {
		return dao;
	}
	
}
