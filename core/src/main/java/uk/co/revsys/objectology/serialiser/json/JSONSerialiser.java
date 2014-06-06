package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.Serialiser;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public abstract class JSONSerialiser<O extends Object> implements Serialiser<O>{

	@Override
	public String serialise(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException {
		return serialiseJSON(objectMapper, object, args).toString();
	}
	
	public abstract Object serialiseJSON(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException;

}
