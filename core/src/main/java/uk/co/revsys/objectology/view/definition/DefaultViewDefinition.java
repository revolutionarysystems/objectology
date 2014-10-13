package uk.co.revsys.objectology.view.definition;

import uk.co.revsys.objectology.view.definition.rule.ReplaceRootRule;

public class DefaultViewDefinition extends ViewDefinition{

    public DefaultViewDefinition() {
        super("default", "/", new ReplaceRootRule("/"));
    }

}
