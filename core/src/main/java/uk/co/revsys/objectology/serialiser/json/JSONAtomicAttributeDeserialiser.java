package uk.co.revsys.objectology.serialiser.json;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONAtomicAttributeDeserialiser<A extends AtomicAttribute> extends JSONStringDeserialiser<A>{

	private final Class<? extends A> type;

	public JSONAtomicAttributeDeserialiser(Class<? extends A> type) {
		this.type = type;
	}
	
	@Override
	public A deserialiseJSON(ObjectMapper objectMapper, String source, Object... args) throws DeserialiserException {
		try {
			A instance = type.newInstance();
			instance.setValueFromString(source);
			instance.setTemplate((AttributeTemplate)args[0]);
			return instance;
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		} catch (ParseException ex) {
            throw new DeserialiserException(ex);
        }
	}

}
