package uk.co.revsys.objectology.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.view.definition.ViewDefinition;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParser;
import uk.co.revsys.objectology.view.definition.parser.ViewDefinitionParsingException;
import uk.co.revsys.resource.repository.model.Resource;
import uk.co.revsys.resource.repository.provider.handler.ResourceHandler;

public class ViewDefinitionLoader implements ResourceHandler {

    private final ViewDefinitionParser parser;
    private final ViewService service;

    public ViewDefinitionLoader(ViewDefinitionParser parser, ViewService service) {
        this.parser = parser;
        this.service = service;
    }

    public ViewDefinition loadViewDefinition(String source) throws ViewDefinitionParsingException, DaoException {
        ViewDefinition definition = parser.parse(source);
        definition = service.update(definition);
        return definition;
    }

    @Override
    public void handle(String path, Resource resource, InputStream contents) throws IOException {
        String source = IOUtils.toString(contents);
        try {
            loadViewDefinition(source);
        } catch (ViewDefinitionParsingException ex) {
            throw new IOException(ex);
        } catch (DaoException ex) {
            throw new IOException(ex);
        }
    }

}
