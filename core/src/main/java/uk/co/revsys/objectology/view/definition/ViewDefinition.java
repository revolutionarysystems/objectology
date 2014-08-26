package uk.co.revsys.objectology.view.definition;

import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class ViewDefinition {

    private String name;
    private String root;
    private ViewDefinitionRule rule;

    public ViewDefinition() {
    }

    public ViewDefinition(String name, String root, ViewDefinitionRule rule) {
        this.name = name;
        this.root = root;
        this.rule = rule;
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

    public ViewDefinitionRule getRule() {
        return rule;
    }

    public void setRule(ViewDefinitionRule rule) {
        this.rule = rule;
    }
}
