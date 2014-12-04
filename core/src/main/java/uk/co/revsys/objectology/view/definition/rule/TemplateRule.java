package uk.co.revsys.objectology.view.definition.rule;

import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class TemplateRule implements ViewDefinitionRule{

    private Map<String, String> mappings;

    public TemplateRule() {
    }

    public TemplateRule(Map<String, String> mappings) {
        this.mappings = mappings;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
    }
    
    @Override
    public Object evaluate(Object object, Object view, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        if(view instanceof Map){
            Map<String, Object> map = (Map<String, Object>) view;
            for(Entry<String, String> mapping: mappings.entrySet()){
                try {
                    Object result = transformer.getPathEvaluator().evaluate(object, mapping.getValue());
                    map.put(mapping.getKey(), result);
                } catch (PathEvaluatorException ex) {
                    throw new TransformException(ex);
                }
            }
            return map;
        }else{
            throw new TransformException("Illegal state " + view.getClass().getName());
        }
    }

}
