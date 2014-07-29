package uk.co.revsys.objectology.action.handler;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.AddToCollectionAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.service.OlogyObjectServiceFactory;

public class AddToCollectionActionHandler extends AttributeActionHandler<AddToCollectionAction, Collection>{

    private final JsonInstanceMapper instanceMapper;

    public AddToCollectionActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }
    
    @Override
    public String getAttributeName(AddToCollectionAction action) {
        return action.getCollection();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Collection collection, AddToCollectionAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String json = getRequiredParameter(request, action.getItem());
            AttributeTemplate memberTemplate = collection.getTemplate().getMemberTemplate();
            Map<String, Object> deserialisationParameters = new HashMap<String, Object>();
            deserialisationParameters.put("template", memberTemplate);
            Attribute item = (Attribute) instanceMapper.deserialise(json, memberTemplate.getAttributeType(), deserialisationParameters);
            collection.getMembers().add(item);
            instance = OlogyObjectServiceFactory.getOlogyInstanceService().update(instance);
            return instance;
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
