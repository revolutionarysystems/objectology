package uk.co.revsys.objectology.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.resource.repository.model.Resource;
import uk.co.revsys.resource.repository.provider.handler.ResourceHandler;

public class TemplateLoader implements ResourceHandler {

    private final OlogyTemplateService<OlogyTemplate> service;
	private final XMLObjectMapper xmlObjectMapper;

    public TemplateLoader(OlogyTemplateService<OlogyTemplate> service, XMLObjectMapper xmlObjectMapper) {
        this.service = service;
        this.xmlObjectMapper = xmlObjectMapper;
    }
    
    public OlogyTemplate loadTemplateFromXML(String xml) throws DeserialiserException, DaoException {
        OlogyTemplate object = xmlObjectMapper.deserialise(xml, OlogyTemplate.class);
        OlogyTemplate previous = null;
        String name = object.getName();
        if (name != null) {
            previous = service.findByName(name);
        }
        if (previous != null) {
            object.setId(previous.getId());
            object = service.update(object);
        } else {
            object = service.create(object);
        }
        return object;
    }

    @Override
    public void handle(String path, Resource resource, InputStream contents) throws IOException {
        String source = IOUtils.toString(contents);
        try {
            loadTemplateFromXML(source);
        } catch (DeserialiserException ex) {
            throw new IOException(ex);
        } catch (DaoException ex) {
            throw new IOException(ex);
        }
    }

}
