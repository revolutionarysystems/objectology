package uk.co.revsys.objectology.serialiser.json;

import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public abstract class JSONAttributeTemplateDeserialiser<A extends AttributeTemplate> extends JSONObjectDeserialiser<A> {

    private final BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap;

    public JSONAttributeTemplateDeserialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        this.templateNatureMap = templateNatureMap;
    }
    
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
            System.out.println("nature = " + nature);
            Class<? extends AttributeTemplate> objectClass = templateNatureMap.get(nature);
            System.out.println("objectClass = " + objectClass);
			return (A) objectClass.newInstance();
		} catch (InstantiationException ex) {
			throw new DeserialiserException(ex);
		} catch (IllegalAccessException ex) {
			throw new DeserialiserException(ex);
		}
	}

    public BidiMap<String, Class<? extends AttributeTemplate>> getTemplateNatureMap() {
        return templateNatureMap;
    }

}
