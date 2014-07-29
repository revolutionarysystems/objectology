package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;
import uk.co.revsys.objectology.model.template.LinkedObjectsTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

@JSONNullType("{}")
public class LinkedObjects extends AbstractAttribute<LinkedObjectsTemplate> implements GeneratedAttribute {

    @JsonValue
    public List<LinkedObject> getObjects() throws DaoException {
        LinkedObjectsTemplate template = getTemplate();
        List<OlogyInstance> instances = OlogyObjectServiceFactory.getOlogyInstanceService().find(template.getType(), new JSONQuery(template.getLink(), getParent().getId()));
        List<LinkedObject> linkedObjects = new ArrayList<LinkedObject>();
        for(OlogyInstance instance: instances){
            LinkedObject linkedObject = new LinkedObject();
            linkedObject.setAssociatedObject(instance);
            linkedObjects.add(linkedObject);
        }
        return linkedObjects;
    }

}
