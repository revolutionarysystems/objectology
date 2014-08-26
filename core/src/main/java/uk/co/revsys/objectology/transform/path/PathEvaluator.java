package uk.co.revsys.objectology.transform.path;

import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface PathEvaluator {

    public Object evaluate(Object object, String path) throws PathEvaluatorException;
    
}
