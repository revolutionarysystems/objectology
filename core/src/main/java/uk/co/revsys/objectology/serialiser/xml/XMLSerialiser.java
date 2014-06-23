package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.mapping.SerialiserException;

public abstract class XMLSerialiser<O extends Object> implements Serialiser<O>{

    @Override
    public String serialise(XMLObjectMapper objectMapper, O object, Object... args) throws SerialiserException {
        return serialiseXML(objectMapper, object, args).asXML();
    }
    
    public abstract Node serialiseXML(XMLObjectMapper objectMapper, O object, Object... args) throws SerialiserException;

}
