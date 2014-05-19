package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.LinkTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONLinkTemplateDeserialiser extends JSONAttributeTemplateDeserialiser<LinkTemplate>{

	@Override
	public LinkTemplate doDeserialiseJSON(LinkTemplate object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
		return object;
	}

}
