package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.collections4.BidiMap;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONAttributeTemplateSerialiser<A extends AttributeTemplate> extends JSONSerialiser<A> {

	private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper;
    private final BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap;

	public JSONAttributeTemplateSerialiser(BidiMap<String, Class<? extends AttributeTemplate>> templateNatureMap) {
        this.templateNatureMap = templateNatureMap;
		jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		jacksonObjectMapper.addMixInAnnotations(AttributeTemplate.class, JacksonAttributeTemplateMixin.class);
		jacksonObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, A object, Object... args) throws SerialiserException {
		JSONObject json = new JSONObject();
		json.put("nature", templateNatureMap.getKey(object.getClass()));
		Attribute value = object.getValue();
		if (value != null) {
			JSONSerialiser valueSerialiser = (JSONSerialiser) objectMapper.getSerialiser(value.getClass());
			json.put("value", valueSerialiser.serialiseJSON(objectMapper, value, object));
		}
		json = doSerialiseJSON(json, objectMapper, object, args);
		return json;
	}

	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, A object, Object args) throws SerialiserException {
		try {
			JSONObject json2 = new JSONObject(jacksonObjectMapper.writeValueAsString(object));
			for (Object key : json2.keySet()) {
				json.put((String) key, json2.get((String) key));
			}
			return json;
		} catch (JsonProcessingException ex) {
			throw new SerialiserException(ex);
		}
	}

}
