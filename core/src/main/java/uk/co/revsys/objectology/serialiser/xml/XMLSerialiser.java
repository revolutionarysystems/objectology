package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.Node;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public abstract class XMLSerialiser<O extends Object> implements Serialiser<O>{

    @Override
    public String serialise(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException {
        return serialiseXML(objectMapper, object, args).asXML();
    }
    
    public abstract Node serialiseXML(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException;

}
