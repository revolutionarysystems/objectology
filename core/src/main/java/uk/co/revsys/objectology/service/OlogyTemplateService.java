package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.query.Query;

public interface OlogyTemplateService<T extends OlogyTemplate> extends PersistedObjectService<T>{
	
	public List<T> find(Query query) throws DaoException;
	
	public T findById(String id) throws DaoException;
	
	public T findByName(String name) throws DaoException;
}
