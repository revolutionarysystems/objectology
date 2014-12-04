package uk.co.revsys.objectology.view.definition.rule;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class FlattenRule implements ViewDefinitionRule {

    private String key;

    public FlattenRule() {
    }

    public FlattenRule(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Object evaluate(Object object, Object view, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        if (view instanceof Map) {
            Map map = (Map) view;
            Object result = map.get(key);
            if (result instanceof Map) {
                map.remove(key);
                map.putAll((Map) result);
                return map;
            } else {
                throw new TransformException("Can not flatten " + view.getClass().getName());
            }
        } else {
            throw new TransformException("Can not flatten to " + view.getClass().getName());
        }
    }

}
