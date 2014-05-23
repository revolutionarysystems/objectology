package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public abstract class JSONAttributeTemplateDeserialiser<A extends AttributeTemplate> extends JSONObjectDeserialiser<A> {

	@Override
	public A deserialiseJSON(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException {
		String nature = source.getString("nature");
		A instance = createObject(objectMapper, source, args);
		Object valueJSON = source.opt("value");
		if (valueJSON != null) {
			JSONDeserialiser valueDeserialiser = (JSONDeserialiser) objectMapper.getDeserialiser(instance.getAttributeType());
			Attribute value = (Attribute) valueDeserialiser.deserialiseJSON(objectMapper, valueJSON, instance);
			System.out.println("value = " + value);
			instance.setValue(value);
		}
		instance = doDeserialiseJSON(instance, objectMapper, source, args);
		return instance;
	}

	public abstract A doDeserialiseJSON(A object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException;

	public A createObject(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException{
		try {
			String nature = source.getString("nature");
			return (A) Class.forName(nature).newInstance();
		} catch (ClassNotFoundException ex) {
			throw new DeserialiserException(ex);
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
