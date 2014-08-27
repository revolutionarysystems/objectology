package uk.co.revsys.objectology.service;

import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.view.View;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public interface ViewService {

    public ViewDefinition findByName(String name) throws DaoException;
    
    public ViewDefinition create(ViewDefinition viewDefinition) throws DaoException;
    
    public ViewDefinition update(ViewDefinition viewDefinition) throws DaoException;
    
    public void delete(ViewDefinition viewDefinition) throws DaoException;
    
    public boolean exists(String name) throws DaoException;
    
    public Object transform(OlogyInstance instance, View view) throws TransformException, DaoException;
    
}
