package uk.co.revsys.objectology.serialiser.json;

import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONAtomicAttributeSerialiser<A extends AtomicAttribute> extends JSONAttributeSerialiser<A> {

	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, A object, Object... args) throws SerialiserException {
        if(object==null){
            return null;
        }
		return object.toString();
	}

}
