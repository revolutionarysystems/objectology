package uk.co.revsys.objectology.serialiser;

import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.mapping.DeserialiserException;

public interface Deserialiser<O extends Object> {

	public O deserialise(XMLObjectMapper objectMapper, String source, Object... args) throws DeserialiserException;
	
}
