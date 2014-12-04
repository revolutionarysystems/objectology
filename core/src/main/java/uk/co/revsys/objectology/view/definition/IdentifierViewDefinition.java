package uk.co.revsys.objectology.view.definition;

import java.util.HashMap;
import uk.co.revsys.objectology.view.definition.rule.TemplateRule;
import uk.co.revsys.objectology.view.definition.rule.ViewDefinitionRule;

public class IdentifierViewDefinition extends ViewDefinition {

    public IdentifierViewDefinition() {
        super("identifier", "/", new HashMap<String, ViewDefinitionRule>() {
            {
                put("main", new TemplateRule(new HashMap<String, String>(){{
                    put("id", "id");
                }}));
            }
        });
    }

}
