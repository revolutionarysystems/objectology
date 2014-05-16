package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.OlogyObject;

public interface OlogyObjectService<O extends OlogyObject>{
	
	public O create(O object) throws DaoException;
	
	public O update(O object) throws DaoException;
	
	public void delete(O object) throws DaoException;
	
}
