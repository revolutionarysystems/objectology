package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.AttributeTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONJacksonAttributeTemplateDeserialiser<A extends AttributeTemplate> extends JSONAttributeTemplateDeserialiser<A>{

	private final Class<? extends A> type;
	private final com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper;

	public JSONJacksonAttributeTemplateDeserialiser(Class<? extends A> type) {
        super(null);
		this.type = type;
		jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		jacksonObjectMapper.addMixInAnnotations(AttributeTemplate.class, JacksonAttributeTemplateMixin.class);
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@Override
	public A doDeserialiseJSON(A object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
		return object;
	}

	@Override
	public A createObject(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException {
		try {
			return jacksonObjectMapper.readValue(source.toString(), type);
		} catch (IOException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
