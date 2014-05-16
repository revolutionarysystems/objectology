package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.serialiser.Deserialiser;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public abstract class JSONDeserialiser<O extends Object, J extends Object> implements Deserialiser<O>{

	@Override
	public O deserialise(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
		return deserialiseJSON(objectMapper, convertStringToJSON(source), args);
	}
	
	public abstract O deserialiseJSON(ObjectMapper objectMapper, J source, Object... args) throws DeserialiserException;
	
	protected abstract J convertStringToJSON(String source);
}
