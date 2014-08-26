package uk.co.revsys.objectology.view.definition.parser;

import uk.co.revsys.objectology.view.definition.ViewDefinition;

public interface ViewDefinitionParser {

    public ViewDefinition parse(String json) throws ViewDefinitionParsingException;
    
}
