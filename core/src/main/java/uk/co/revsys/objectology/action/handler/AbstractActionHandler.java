package uk.co.revsys.objectology.action.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.action.handler.exception.MissingParameterException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.condition.FailedConditionException;
import uk.co.revsys.objectology.action.model.Action;
import uk.co.revsys.objectology.condition.Condition;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public abstract class AbstractActionHandler<A extends Action> implements ActionHandler<A> {

    @Override
    public OlogyInstance invoke(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException {
        try {
            checkConditions(instance, action, request);
            return doInvoke(instance, action, request);
        } catch (FailedConditionException ex) {
            throw new ActionInvocationException(ex);
        }
    }

    protected void checkConditions(OlogyInstance instance, A action, ActionRequest request) throws FailedConditionException {
        for (Condition condition : action.getConditions()) {
            condition.check(instance);
        }
    }

    public abstract OlogyInstance doInvoke(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException;

    protected String getRequiredParameter(ActionRequest request, String parameterName) throws MissingParameterException {
        if (!request.getParameters().containsKey(parameterName)) {
            throw new MissingParameterException("Expected request parameter: " + parameterName);
        }
        String value = request.getParameters().get(parameterName);
        return value;
    }

}
