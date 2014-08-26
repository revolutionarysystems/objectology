package uk.co.revsys.objectology.view.definition.rule;

import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.OlogyView;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.TransformException;

public class OneToOneMappingRule implements ViewDefinitionRule{

    private String label;
    private String path;

    public OneToOneMappingRule() {
    }

    public OneToOneMappingRule(String label, String path) {
        this.label = label;
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public OlogyView evaluate(Object object, OlogyView result, ViewDefinition transform, OlogyTransformer transformer) throws TransformException{
        try {
            System.out.println(getLabel());
            System.out.println(getPath());
            result.put(getLabel(), transformer.getPathEvaluator().evaluate(object, getPath()));
            return result;
        } catch (PathEvaluatorException ex) {
            throw new TransformException("Unable to evaluate path " + getPath(), ex);
        }
    }


}
