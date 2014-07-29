package uk.co.revsys.objectology.action.handler;

import java.util.HashMap;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.action.model.AddToCollectionAction;
import uk.co.revsys.objectology.action.model.UpdateAttributeAction;
import uk.co.revsys.objectology.mapping.json.JsonInstanceMapper;

public class DefaultActionHandlerFactory extends ActionHandlerFactory {

    public DefaultActionHandlerFactory(final JsonInstanceMapper instanceMapper) {
        super(new HashMap<Class<? extends Action>, ActionHandler>() {
            {
                put(UpdateAttributeAction.class, new UpdateAttributeActionHandler(instanceMapper));
                put(AddToCollectionAction.class, new AddToCollectionActionHandler(instanceMapper));
            }
        });
    }

}
