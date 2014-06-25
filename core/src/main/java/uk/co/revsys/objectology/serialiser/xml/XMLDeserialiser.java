package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.revsys.objectology.serialiser.Deserialiser;
import uk.co.revsys.objectology.mapping.DeserialiserException;
import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;

public abstract class XMLDeserialiser<O extends Object> implements Deserialiser<O>{

    private static final Logger LOG = LoggerFactory.getLogger(XMLDeserialiser.class);
    
	@Override
	public O deserialise(XMLObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
		try {
			return deserialise(objectMapper, DocumentHelper.parseText(source).getRootElement(), args);
		} catch (DocumentException ex) {
            LOG.error("Invalid XML: " + source);
			throw new DeserialiserException(ex);
		}
	}
	
	public abstract O deserialise(XMLObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException;

}
