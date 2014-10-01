package uk.co.revsys.objectology.action.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.UpdateDictionaryEntryAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateDictionaryEntryActionHandler extends AttributeActionHandler<UpdateDictionaryEntryAction, Dictionary<Attribute>>{

    private final JsonInstanceMapper instanceMapper;

    public UpdateDictionaryEntryActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }
    
    @Override
    public String getAttributeName(UpdateDictionaryEntryAction action) {
        return action.getDictionary();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Dictionary<Attribute> dictionary, UpdateDictionaryEntryAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String key = getRequiredParameter(request, "key");
            String value = getRequiredParameter(request, "value");
            Attribute member = instanceMapper.deserialise(value, dictionary.getTemplate().getMemberTemplate());
            dictionary.put(key, member);
            ServiceFactory.getOlogyInstanceService().update(instance);
            return instance;
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
