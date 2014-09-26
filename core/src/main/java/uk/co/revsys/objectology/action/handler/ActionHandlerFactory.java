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
        ActionHandler handler = handlers.get(actionType);
        if(handler instanceof ActionHandlerFactoryAware){
            ((ActionHandlerFactoryAware)handler).setActionHandlerFactory(this);
        }
        return handler;
    }
    
    public ActionHandler getHandler(Action action){
        return getHandler(action.getClass());
    }
    
    public void setHandler(Class<? extends Action> actionType, ActionHandler handler){
        handlers.put(actionType, handler);
    }
    
}
