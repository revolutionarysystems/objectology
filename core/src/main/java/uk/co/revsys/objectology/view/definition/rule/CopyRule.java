package uk.co.revsys.objectology.view.definition.rule;

import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class CopyRule implements ViewDefinitionRule{

    private String path;

    public CopyRule() {
    }

    public CopyRule(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public Object evaluate(Object object, Object view, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        try {
            return transformer.getPathEvaluator().evaluate(object, path);
        } catch (PathEvaluatorException ex) {
            throw new TransformException(ex);
        }
    }

}
