package uk.co.revsys.objectology.service;

import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface OlogyInstanceService<I extends OlogyInstance> extends OlogyObjectService<I>{

	public List<I> findAll(String type) throws DaoException;
	
	public I findById(String type, String id) throws DaoException;
	
}
