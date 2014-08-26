package uk.co.revsys.objectology.view.definition.rule;

import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.OlogyView;
import uk.co.revsys.objectology.transform.path.PathEvaluatorException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.TransformException;

public class DelegateRule implements ViewDefinitionRule {

    private String label;
    private String path;
    private ViewDefinitionRule rule;

    public DelegateRule() {
    }

    public DelegateRule(String label, String path, ViewDefinitionRule rule) {
        this.label = label;
        this.path = path;
        this.rule = rule;
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

    public ViewDefinitionRule getRule() {
        return rule;
    }

    public void setRule(ViewDefinitionRule rule) {
        this.rule = rule;
    }

    @Override
    public OlogyView evaluate(Object object, OlogyView result, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        try {
            Object target = transformer.getPathEvaluator().evaluate(object, path);
            if (target instanceof List) {
                List parts = (List)target;
                List partResults = new LinkedList();
                for (Object part : parts) {
                    OlogyView partView = new OlogyView();
                    Object partResult = getRule().evaluate(part, partView, transform, transformer);
                    partResults.add(partResult);
                }
                result.put(getLabel(), partResults);
            }else{
                OlogyView partView = new OlogyView();
                result.put(getLabel(), getRule().evaluate(target, partView, transform, transformer));
            }
            return result;
        } catch (PathEvaluatorException ex) {
            throw new TransformException("Unable to evalute path: " + getPath(), ex);
        }
    }

}
