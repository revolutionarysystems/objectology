package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.OlogyObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public abstract class AbstractLink<A extends AttributeTemplate> extends AbstractAttribute<A>{

    public abstract String getReference();
    
    public abstract OlogyObject getAssociatedObject() throws DaoException;
    
}
