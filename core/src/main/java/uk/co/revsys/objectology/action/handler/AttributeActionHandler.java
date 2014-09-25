package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public abstract class AttributeActionHandler<A extends Action, T extends Attribute> extends AbstractActionHandler<A>{

    public AttributeActionHandler() {
    }

    public abstract  String getAttributeName(A action);
    
    @Override
    public OlogyInstance invoke(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException {
        T attribute = (T) instance.getAttribute(getAttributeName(action));
        if(attribute == null){
            throw new ActionInvocationException("Attribute " + getAttributeName(action) + " does not exist");
        }
        return doInvoke(instance, attribute, action, request);
    }
    
    public abstract OlogyInstance doInvoke(OlogyInstance instance, T attribute, A action, ActionRequest request) throws ActionInvocationException;

}
