package uk.co.revsys.objectology.view.definition;

import java.util.HashMap;
import uk.co.revsys.objectology.view.definition.rule.CopyRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class DefaultViewDefinition extends ViewDefinition{

    public DefaultViewDefinition() {
        super("default", "/", new HashMap<String, ViewDefinitionRule>() {
            {
                put("main", new CopyRule("/"));
            }
        });
    }

}
