package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public abstract class JSONAttributeTemplateDeserialiser<A extends AttributeTemplate> extends JSONObjectDeserialiser<A> {

	@Override
	public A deserialiseJSON(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException {
		try {
			String nature = source.getString("nature");
			A instance = (A) Class.forName(nature).newInstance();
			Object valueJSON = source.opt("value");
			if(valueJSON!=null){
				JSONDeserialiser valueDeserialiser = (JSONDeserialiser) objectMapper.getDeserialiser(instance.getAttributeType());
				Attribute value = (Attribute) valueDeserialiser.deserialiseJSON(objectMapper, valueJSON, instance);
				System.out.println("value = " + value);
				instance.setValue(value);
			}
			instance = doDeserialiseJSON(instance, objectMapper, source, args);
			return instance;
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		} catch (ClassNotFoundException ex) {
			throw new DeserialiserException(ex);
		}
	}
	
	public abstract A doDeserialiseJSON(A object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException;

}
