package uk.co.revsys.objectology.view.definition.rule;

import java.util.LinkedList;
import java.util.List;
import uk.co.revsys.objectology.transform.OlogyTransformer;
import uk.co.revsys.objectology.transform.OlogyView;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.transform.TransformException;

public class ViewDefinitionRuleSet implements ViewDefinitionRule {

    private List<ViewDefinitionRule> rules = new LinkedList<ViewDefinitionRule>();

    public ViewDefinitionRuleSet() {
    }
    
    public ViewDefinitionRuleSet(List<ViewDefinitionRule> rules){
        this.rules = rules;
    }

    public List<ViewDefinitionRule> getRules() {
        return rules;
    }

    public void setRules(List<ViewDefinitionRule> rules) {
        this.rules = rules;
    }
    
    public void addRule(ViewDefinitionRule rule){
        this.rules.add(rule);
    }

    @Override
    public Object evaluate(Object object, OlogyView result, ViewDefinition transform, OlogyTransformer transformer) throws TransformException {
        for(ViewDefinitionRule rule: getRules()){
            result = (OlogyView) rule.evaluate(object, result, transform, transformer);
        }
        return result;
    }
    
}
