package uk.co.revsys.objectology.action.handler;

import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.UpdateDictionaryAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateDictionaryActionHandler extends AttributeActionHandler<UpdateDictionaryAction, Dictionary<Attribute>>{

    private final JsonInstanceMapper instanceMapper;

    public UpdateDictionaryActionHandler(JsonInstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }
    
    @Override
    public String getAttributeName(UpdateDictionaryAction action) {
        return action.getDictionary();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Dictionary<Attribute> dictionary, UpdateDictionaryAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String json = getRequiredParameter(request, action.getDictionary());
            Dictionary<Attribute> newDictionary = (Dictionary) instanceMapper.deserialise(json, dictionary.getTemplate());
            for(Entry<String, Attribute> entry: newDictionary.getMap().entrySet()){
                dictionary.put(entry.getKey(), entry.getValue());
            }
            ServiceFactory.getOlogyInstanceService().update(instance);
            return instance;
        } catch (DeserialiserException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
