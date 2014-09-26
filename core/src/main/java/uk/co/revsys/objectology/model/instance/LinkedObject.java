package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.json.JSONNullType;
import uk.co.revsys.objectology.mapping.json.serialise.LinkSerialiser;
import uk.co.revsys.objectology.model.template.LinkedObjectTemplate;
import uk.co.revsys.objectology.query.JSONQuery;
import uk.co.revsys.objectology.service.ServiceFactory;

@JSONNullType("{}")
@JsonSerialize(using = LinkSerialiser.class)
public class LinkedObject extends AbstractLink<LinkedObject, LinkedObjectTemplate> implements GeneratedAttribute{

    private OlogyInstance associatedObject;

    @Override
    public String getReference() {
        try {
            OlogyInstance associatedObject = getAssociatedObject();
            if (associatedObject == null) {
                return "";
            }
            return associatedObject.getId();
        } catch (DaoException ex) {
            return "";
        }
    }

    public void setAssociatedObject(OlogyInstance associatedObject) {
        this.associatedObject = associatedObject;
    }

    @Override
    public OlogyInstance getAssociatedObject() throws DaoException {
        if (associatedObject == null) {
            LinkedObjectTemplate template = getTemplate();
            List<OlogyInstance> instances = ServiceFactory.getOlogyInstanceService().find(template.getType(), new JSONQuery(template.getLink(), getParent().getId()));
            if (!instances.isEmpty()) {
                 associatedObject = instances.get(0);
            }
        }
        return associatedObject;
    }

    @Override
    public LinkedObject copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
