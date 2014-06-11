package uk.co.revsys.objectology.serialiser.json;

import java.util.Map.Entry;
import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.model.template.OlogyTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONOlogyTemplateSerialiser extends JSONAttributeTemplateSerialiser<OlogyTemplate> {

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, OlogyTemplate object, Object args) throws SerialiserException{
		json.put("id", object.getId());
		json.put("name", object.getName());
		json.put("type", object.getType());
		JSONObject attributeTemplates = new JSONObject();
		json.put("attributes", attributeTemplates);
		for (Entry<String, AttributeTemplate> attributeTemplate : object.getAttributeTemplates().entrySet()) {
			AttributeTemplate attrTemplate = attributeTemplate.getValue();
			JSONSerialiser attributeSerialiser = (JSONSerialiser) objectMapper.getSerialiser(attrTemplate.getClass());
			attributeTemplates.put(attributeTemplate.getKey(), attributeSerialiser.serialiseJSON(objectMapper, attrTemplate, args));
		}
		return json;
	}

}
