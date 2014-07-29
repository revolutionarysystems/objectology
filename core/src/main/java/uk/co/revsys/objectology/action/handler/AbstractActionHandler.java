package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.Action;

public abstract class AbstractActionHandler<A extends Action> implements ActionHandler<A>{

    protected String getRequiredParameter(ActionRequest request, String parameterName) throws MissingParameterException{
        if (!request.getParameters().containsKey(parameterName)) {
            throw new MissingParameterException("Expected request parameter: " + parameterName);
        }
        String value = request.getParameters().get(parameterName);
        return value;
    }
    
}
