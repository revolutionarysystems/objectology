package uk.co.revsys.objectology.view.definition;

import java.util.HashMap;
import java.util.Map;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class ViewDefinition {

    private String name;
    private String root;
    private Map<String, ViewDefinitionRule> rules = new HashMap<String, ViewDefinitionRule>();

    public ViewDefinition() {
    }

    public ViewDefinition(String name, String root, Map<String, ViewDefinitionRule> rules) {
        this.name = name;
        this.root = root;
        this.rules = rules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Map<String, ViewDefinitionRule> getRules() {
        return rules;
    }

    public void setRules(Map<String, ViewDefinitionRule> rules) {
        this.rules = rules;
    }

}
