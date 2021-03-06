package uk.co.revsys.objectology.action.handler;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.RemoveFromCollectionAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Collection;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.template.AtomicAttributeTemplate;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.service.ServiceFactory;

public class RemoveFromCollectionActionHandler extends AttributeActionHandler<RemoveFromCollectionAction, Collection<Attribute>> {

    private final JsonInstanceMapper instanceMapper;

    public RemoveFromCollectionActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Override
    public String getAttributeName(RemoveFromCollectionAction action) {
        return action.getCollection();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Collection<Attribute> collection, RemoveFromCollectionAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String json = getRequiredParameter(request, action.getItem());
            AttributeTemplate memberTemplate = collection.getTemplate().getMemberTemplate();
            Attribute item = instanceMapper.deserialise(json, memberTemplate);
            collection.remove(item);
            instance = ServiceFactory.getOlogyInstanceService().update(instance);
            return instance;
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
