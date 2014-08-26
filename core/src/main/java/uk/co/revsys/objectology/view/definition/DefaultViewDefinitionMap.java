package uk.co.revsys.objectology.view.definition;

import java.util.HashMap;

public class DefaultViewDefinitionMap extends HashMap<String, ViewDefinition>{

    public DefaultViewDefinitionMap() {
        put("default", new DefaultViewDefinition());
        put("identifier", new IdentifierViewDefinition());
    }

}
