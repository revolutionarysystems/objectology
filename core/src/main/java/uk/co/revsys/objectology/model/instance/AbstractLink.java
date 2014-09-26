package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.PersistedObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public abstract class AbstractLink<L extends AbstractLink, T extends AttributeTemplate> extends AbstractAttribute<L, T>{

    public abstract String getReference();
    
    public abstract PersistedObject getAssociatedObject() throws DaoException;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractLink){
            obj = ((AbstractLink)obj).getReference();
        }
        if(getReference() == null){
            return obj == null;
        }
        return getReference().equals(obj);
    }
    
    
    
}
