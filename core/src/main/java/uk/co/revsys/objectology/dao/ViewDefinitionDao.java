package uk.co.revsys.objectology.dao;

import uk.co.revsys.objectology.view.definition.ViewDefinition;

public interface ViewDefinitionDao {

    public ViewDefinition findByName(String name) throws DaoException;

    public ViewDefinition create(ViewDefinition viewDefinition) throws DaoException, DuplicateKeyException;

    public ViewDefinition update(ViewDefinition viewDefinition) throws DaoException;
    
    public void delete(ViewDefinition viewDefinition) throws DaoException;

    public boolean exists(String name) throws DaoException;

}
