package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;

public interface OlogyTemplateService<T extends OlogyTemplate> extends OlogyObjectService<T>{

	public List<T> findAll() throws DaoException;
	
	public T findById(String id) throws DaoException;
	
	public T findByName(String name) throws DaoException;
}
