package uk.co.revsys.objectology.view.definition;

import uk.co.revsys.objectology.view.definition.rule.OneToOneMappingRule;

public class IdentifierViewDefinition extends ViewDefinition{

    public IdentifierViewDefinition() {
        super("identifier", "/", new OneToOneMappingRule("id", "/id"));
    }

}
