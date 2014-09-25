package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.action.model.CompoundAction;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class CompoundActionHandler extends AbstractActionHandler<CompoundAction>{

    private final ActionHandlerFactory actionHandlerFactory;

    public CompoundActionHandler(ActionHandlerFactory actionHandlerFactory) {
        this.actionHandlerFactory = actionHandlerFactory;
    }
    
    @Override
    public OlogyInstance invoke(OlogyInstance instance, CompoundAction compoundAction, ActionRequest request) throws ActionInvocationException {
        for(Action action: compoundAction.getActions()){
            ActionHandler handler = actionHandlerFactory.getHandler(action);
            instance = handler.invoke(instance, action, request);
        }
        return instance;
    }

}
