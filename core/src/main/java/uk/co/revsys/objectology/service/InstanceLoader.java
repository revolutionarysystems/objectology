package uk.co.revsys.objectology.service;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import uk.co.revsys.objectology.dao.DaoException;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.model.instance.OlogyInstance;
import uk.co.revsys.resource.repository.model.Resource;
import uk.co.revsys.resource.repository.provider.handler.ResourceHandler;

public class InstanceLoader implements ResourceHandler {

    private final OlogyInstanceService<OlogyInstance> service;
    private final XMLObjectMapper xmlObjectMapper;

    public InstanceLoader(OlogyInstanceService<OlogyInstance> service, XMLObjectMapper xmlObjectMapper) {
        this.service = service;
        this.xmlObjectMapper = xmlObjectMapper;
    }

    public OlogyInstance loadInstanceFromXML(String xml) throws DeserialiserException, DaoException {
        OlogyInstance object = xmlObjectMapper.deserialise(xml, OlogyInstance.class);
        OlogyInstance previous = null;
        String name = object.getName();
        if(name!=null){
            previous = service.findByName(object.getType(), name);
        }
        if(previous==null){
            object = service.create(object);
        }else{
            object.setId(previous.getId());
            object = service.update(object);
        }
        return object;
    }

    @Override
    public void handle(String path, Resource resource, InputStream contents) throws IOException {
        String source = IOUtils.toString(contents);
        try {
            loadInstanceFromXML(source);
        } catch (DeserialiserException ex) {
            throw new IOException(ex);
        } catch (DaoException ex) {
            throw new IOException(ex);
        }
    }

}
