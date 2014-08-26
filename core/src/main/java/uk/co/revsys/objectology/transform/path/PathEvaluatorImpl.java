package uk.co.revsys.objectology.transform.path;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class PathEvaluatorImpl implements PathEvaluator{

    private JexlEngine jexlEngine;

    public PathEvaluatorImpl() {
        jexlEngine = new JexlEngine();
        jexlEngine.setSilent(false);
        jexlEngine.setLenient(false);
    }

    @Override
    public Object evaluate(Object object, String path) throws PathEvaluatorException{
        Expression expression = jexlEngine.createExpression(path);
        JexlContext context = new MapContext();
        context.set("$", object);
        return expression.evaluate(context);
    }
}
