package uk.co.revsys.objectology.view.definition.rule;

import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.OlogyView;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.TransformException;

public class ReplaceRootRule implements ViewDefinitionRule {

    private String path;

    public ReplaceRootRule() {
    }

    public ReplaceRootRule(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Object evaluate(Object object, OlogyView result, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        try {
            return transformer.getPathEvaluator().evaluate(object, getPath());
        } catch (PathEvaluatorException ex) {
            throw new TransformException("Unable to evaluate path " + getPath(), ex);
        }
    }

}
