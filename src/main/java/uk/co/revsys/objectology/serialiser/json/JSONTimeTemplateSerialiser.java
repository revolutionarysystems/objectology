package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONTimeTemplateSerialiser extends JSONAttributeTemplateSerialiser<TimeTemplate>{

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, TimeTemplate object, Object args) throws SerialiserException {
		return json;
	}

}
