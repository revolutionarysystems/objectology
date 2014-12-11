package uk.co.revsys.objectology.action.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.text.StrSubstitutor;
import uk.co.revsys.objectology.action.handler.exception.ActionInvocationException;
import uk.co.revsys.objectology.action.ActionRequest;
import uk.co.revsys.objectology.action.model.AttributeAction;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.objectology.transform.path.JXPathEvaluator;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;

public abstract class AttributeActionHandler<A extends AttributeAction, T extends Attribute> extends AbstractActionHandler<A> {

    private PathEvaluator pathEvaluator = new JXPathEvaluator();

    public AttributeActionHandler() {
    }

    public abstract String getAttributeName(A action);

    public OlogyInstance getBase(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException {
        OlogyInstance base = instance;
        if (action.getBase() != null) {
            try {
                base = (OlogyInstance) pathEvaluator.evaluate(instance.getAttributes(), action.getBase());
            } catch (PathEvaluatorException ex) {
                throw new ActionInvocationException(ex);
            }
        }
        return base;
    }
    
    public T getAttribute(OlogyInstance base, A action, ActionRequest request) throws ActionInvocationException {
        String attributeName = getAttributeName(action);
        if(attributeName.contains("${")){
            attributeName = StrSubstitutor.replace(attributeName, request.getParameters());
        }
        T attribute = (T) base.getAttribute(attributeName);
        return attribute;
    }

    @Override
    public OlogyInstance doInvoke(OlogyInstance instance, A action, ActionRequest request) throws ActionInvocationException {
        OlogyInstance base = getBase(instance, action, request);
        if (base == null) {
            throw new ActionInvocationException("Part " + action.getBase() + " does not exist");
        }
        T attribute = getAttribute(base, action, request);
        if (attribute == null) {
            throw new ActionInvocationException("Attribute " + getAttributeName(action) + " does not exist");
        }
        return doInvoke(instance, attribute, action, request);
    }

    public abstract OlogyInstance doInvoke(OlogyInstance instance, T attribute, A action, ActionRequest request) throws ActionInvocationException;

}
