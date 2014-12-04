package uk.co.revsys.objectology.view.definition.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class FilterRule implements ViewDefinitionRule {

    private List<String> includes;
    private List<String> excludes;

    public FilterRule() {
    }

    public FilterRule(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    @Override
    public Object evaluate(Object object, Object view, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        if (view instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) view;
            Map<String, Object> result;
            if (includes != null) {
                result = new HashMap<String, Object>();
                for (Entry<String, Object> entry : map.entrySet()) {
                    if (includes.contains(entry.getKey())) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                result = map;
                if(excludes!=null){
                    for(String exclude: excludes){
                        result.remove(exclude);
                    }
                }
            }
            return result;
        } else {
            throw new TransformException("Unable to filter type " + view.getClass().getName());
        }
    }

}
