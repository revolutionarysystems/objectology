package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public abstract class JSONAttributeTemplateSerialiser<A extends AttributeTemplate> extends JSONSerialiser<A>{

	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, A object, Object... args) throws SerialiserException {
		JSONObject json = new JSONObject();
		json.put("nature", object.getClass().getName());
		Attribute value = object.getValue();
		if (value != null) {
			JSONSerialiser valueSerialiser = (JSONSerialiser) objectMapper.getSerialiser(value.getClass());
			json.put("value", valueSerialiser.serialiseJSON(objectMapper, value, object));
		}
		json = doSerialiseJSON(json, objectMapper, object, args);
		return json;
	}
	
	public abstract JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, A object, Object args) throws SerialiserException;
	
}
