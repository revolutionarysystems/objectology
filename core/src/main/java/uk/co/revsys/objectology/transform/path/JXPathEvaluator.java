package uk.co.revsys.objectology.transform.path;

import org.apache.commons.jxpath.JXPathContext;

public class JXPathEvaluator implements PathEvaluator{

    @Override
    public Object evaluate(Object object, String path) throws PathEvaluatorException {
        JXPathContext context = JXPathContext.newContext(object);
        context.setLenient(true);
        return context.getValue(path);
    }

}
