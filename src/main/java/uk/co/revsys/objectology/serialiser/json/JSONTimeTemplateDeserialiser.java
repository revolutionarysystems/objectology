package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.TimeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONTimeTemplateDeserialiser extends JSONAttributeTemplateDeserialiser<TimeTemplate>{

	@Override
	public TimeTemplate doDeserialiseJSON(TimeTemplate object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
		return object;
	}

}
