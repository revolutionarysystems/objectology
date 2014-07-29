package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface ActionHandler<A extends Action> {

    public OlogyInstance invoke(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException;
    
}
