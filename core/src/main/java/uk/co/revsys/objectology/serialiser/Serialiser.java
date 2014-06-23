package uk.co.revsys.objectology.serialiser;

import uk.co.revsys.objectology.mapping.xml.XMLObjectMapper;
import uk.co.revsys.objectology.mapping.SerialiserException;

public interface Serialiser<O extends Object> {

	public String serialise(XMLObjectMapper objectMapper, O object, Object... args) throws SerialiserException;
	
}
