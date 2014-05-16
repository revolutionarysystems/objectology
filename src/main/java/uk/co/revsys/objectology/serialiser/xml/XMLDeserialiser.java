package uk.co.revsys.objectology.serialiser.xml;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import uk.co.revsys.objectology.serialiser.Deserialiser;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public abstract class XMLDeserialiser<O extends Object> implements Deserialiser<O>{

	@Override
	public O deserialise(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
		try {
			return deserialise(objectMapper, DocumentHelper.parseText(source).getRootElement(), args);
		} catch (DocumentException ex) {
			throw new DeserialiserException(ex);
		}
	}
	
	public abstract O deserialise(ObjectMapper objectMapper, Node source, Object... args) throws DeserialiserException;

}
