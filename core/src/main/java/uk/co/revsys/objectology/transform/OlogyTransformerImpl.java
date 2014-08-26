package uk.co.revsys.objectology.transform;

import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.transform.path.PathEvaluatorImpl;
import uk.co.revsys.objectology.transform.path.PathEvaluator;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;
import uk.co.revsys.objectology.model.instance.OlogyInstance;

public class OlogyTransformerImpl implements OlogyTransformer {

    private final PathEvaluator pathEvaluator = new PathEvaluatorImpl();

    @Override
    public Object transform(OlogyInstance instance, ViewDefinition viewDefinition) throws TransformException {
        OlogyView view = new OlogyView();
        Object root = instance;
        String rootPath = viewDefinition.getRoot();
        if (rootPath != null) {
            try {
                root = pathEvaluator.evaluate(instance, rootPath);
            } catch (PathEvaluatorException ex) {
                throw new TransformException("Unable to find root " + rootPath, ex);
            }
        }
        ViewDefinitionRule rootRule = viewDefinition.getRule();
        Object result = rootRule.evaluate(root, view, viewDefinition, this);
        return result;
    }

    @Override
    public PathEvaluator getPathEvaluator() {
        return pathEvaluator;
    }
}
