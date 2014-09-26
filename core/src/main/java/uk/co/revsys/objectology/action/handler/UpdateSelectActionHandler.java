package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.InvalidStateException;
import uk.co.revsys.objectology.action.model.UpdateSelectAction;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.model.instance.Property;
import uk.co.revsys.objectology.service.ServiceFactory;

public class UpdateSelectActionHandler extends AttributeActionHandler<UpdateSelectAction, Property>{

    @Override
    public String getAttributeName(UpdateSelectAction action) {
        return action.getSelect();
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, Property select, UpdateSelectAction action, ActionRequest request) throws ActionInvocationException {
        try {
            String requiredExistingValue = action.getExistingValue();
            if(requiredExistingValue != null){
                String existingValue = select.getValue();
                if(!requiredExistingValue.equals(existingValue)){
                    throw new InvalidStateException("Expected " + requiredExistingValue + ", found " + existingValue);
                }
            }
            String newValue = action.getValue();
            if(newValue == null){
                newValue = getRequiredParameter(request, action.getSelect());
            }
            select.setValue(newValue);
            return ServiceFactory.getOlogyInstanceService().update(instance);
        } catch (ValidationException ex) {
            throw new ActionInvocationException(ex);
        } catch (DaoException ex) {
            throw new ActionInvocationException(ex);
        }
    }

}
