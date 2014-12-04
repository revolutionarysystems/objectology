package uk.co.revsys.objectology.view.definition.rule;

import java.util.List;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.TransformException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;

public class ChainRule implements ViewDefinitionRule{

    private List<String> rules;

    public ChainRule() {
    }

    public ChainRule(List<String> rules) {
        this.rules = rules;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    @Override
    public Object evaluate(Object object, Object view, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        for(String rule: rules){
            view = transform.getRules().get(rule).evaluate(object, view, transform, transformer);
        }
        return view;
    }
    
}
