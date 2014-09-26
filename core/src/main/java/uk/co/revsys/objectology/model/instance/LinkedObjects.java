package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.service.ServiceFactory;

@JSONNullType("{}")
public class LinkedObjects extends AbstractAttribute<LinkedObjects, LinkedObjectsTemplate> implements GeneratedAttribute {

    @JsonValue
    public List<LinkedObject> getObjects() throws DaoException {
        LinkedObjectsTemplate template = getTemplate();
        List<OlogyInstance> instances = ServiceFactory.getOlogyInstanceService().find(template.getType(), new JSONQuery(template.getLink(), getParent().getId()));
        List<LinkedObject> linkedObjects = new ArrayList<LinkedObject>();
        for(OlogyInstance instance: instances){
            LinkedObject linkedObject = new LinkedObject();
            linkedObject.setAssociatedObject(instance);
            linkedObjects.add(linkedObject);
        }
        return linkedObjects;
    }
    
    @JsonIgnore
    public List<OlogyInstance> getAssociatedObjects() throws DaoException{
        List<OlogyInstance> instances = new LinkedList<OlogyInstance>();
        for(LinkedObject linkedObject: getObjects()){
            instances.add(linkedObject.getAssociatedObject());
        }
        return instances;
    }

    @Override
    public LinkedObjects copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
