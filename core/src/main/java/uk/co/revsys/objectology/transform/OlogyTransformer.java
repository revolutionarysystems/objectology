package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public interface OlogyTransformer {

    public Object transform(OlogyInstance instance, ViewDefinition viewDefinition) throws TransformException;
    
    public PathEvaluator getPathEvaluator();

}
