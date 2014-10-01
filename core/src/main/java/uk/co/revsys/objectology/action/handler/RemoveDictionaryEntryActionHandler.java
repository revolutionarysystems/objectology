package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.RemoveDictionaryEntryAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.instance.Dictionary;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.service.ServiceFactory;

public class RemoveDictionaryEntryActionHandler extends AttributeActionHandler<RemoveDictionaryEntryAction, Dictionary> {

    @Override
    public String getAttributeName(RemoveDictionaryEntryAction action) {
        return action.getDictionary();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Dictionary dictionary, RemoveDictionaryEntryAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String key = getRequiredParameter(request, "key");
            dictionary.remove(key);
            ServiceFactory.getOlogyInstanceService().update(instance);
            return instance;
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
