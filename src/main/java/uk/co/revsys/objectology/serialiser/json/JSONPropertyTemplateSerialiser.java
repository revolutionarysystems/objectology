package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONPropertyTemplateSerialiser extends JSONAttributeTemplateSerialiser<PropertyTemplate>{

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, PropertyTemplate object, Object args) throws SerialiserException {
		return json;
	}

}
