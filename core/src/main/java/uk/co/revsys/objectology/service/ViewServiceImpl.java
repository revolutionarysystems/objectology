package uk.co.revsys.objectology.service;

import java.util.Map;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.dao.ViewDefinitionDao;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class ViewServiceImpl implements ViewService {

    private ViewDefinitionDao dao;
    private OlogyTransformer transformer;
    private Map<String, ViewDefinition> defaultViewDefinitions;

    public ViewServiceImpl(ViewDefinitionDao dao, OlogyTransformer transformer, Map<String, ViewDefinition> defaultViewDefinitions) {
        this.dao = dao;
        this.transformer = transformer;
        this.defaultViewDefinitions = defaultViewDefinitions;
    }

    @Override
    public ViewDefinition findByName(String name) throws DaoException {
        return dao.findByName(name);
    }

    @Override
    public ViewDefinition create(ViewDefinition viewDefinition) throws DaoException {
        return dao.create(viewDefinition);
    }

    @Override
    public ViewDefinition update(ViewDefinition viewDefinition) throws DaoException {
        return dao.update(viewDefinition);
    }

    @Override
    public void delete(ViewDefinition viewDefinition) throws DaoException {
        dao.delete(viewDefinition);
    }

    @Override
    public boolean exists(String name) throws DaoException {
        return dao.exists(name);
    }
    
    @Override
    public Object transform(OlogyInstance instance, String view) throws TransformException, DaoException {
        if(view == null){
            view = "default";
        }
        ViewDefinition viewDefinition = defaultViewDefinitions.get(view);
        if(viewDefinition == null){
            viewDefinition = findByName(view);
        }
        return transformer.transform(instance, viewDefinition);
    }

}
