package uk.co.revsys.objectology.action.handler;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.action.model.Action;

public class ActionHandlerFactory {

    private Map<Class<? extends Action>, ActionHandler> handlers = new HashMap<Class<? extends Action>, ActionHandler>();

    public ActionHandlerFactory() {
    }

    public ActionHandlerFactory(Map<Class<? extends Action>, ActionHandler> handlers) {
        this.handlers = handlers;
    }

    public ActionHandler getHandler(Class<? extends Action> actionType){
        return handlers.get(actionType);
    }
    
    public ActionHandler getHandler(Action action){
        return getHandler(action.getClass());
    }
    
}
