package uk.co.revsys.objectology.view.definition.rule;

import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.OlogyView;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.TransformException;

public interface ViewDefinitionRule {

    public Object evaluate(Object object, OlogyView result, ViewDefinition transform, OlogyTransformer transformer) throws TransformException;
    
}
