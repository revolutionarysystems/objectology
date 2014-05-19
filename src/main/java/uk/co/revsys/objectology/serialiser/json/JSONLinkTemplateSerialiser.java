package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONLinkTemplateSerialiser extends JSONAttributeTemplateSerialiser<LinkTemplate>{

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, LinkTemplate object, Object args) throws SerialiserException {
		return json;
	}

}
