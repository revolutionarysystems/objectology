package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.model.instance.Link;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONLinkSerialiser extends JSONAttributeSerialiser<Link>{

	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, Link object, Object... args) throws SerialiserException {
		return object.getReference();
	}

}
