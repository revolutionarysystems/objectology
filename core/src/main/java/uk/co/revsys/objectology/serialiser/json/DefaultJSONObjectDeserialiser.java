package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import org.json.JSONObject;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class DefaultJSONObjectDeserialiser<O extends Object> extends JSONObjectDeserialiser<O> {

	private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

	private Class<? extends O> type;

	public DefaultJSONObjectDeserialiser(Class<? extends O> type) {
		this.type = type;
		jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public O deserialiseJSON(ObjectMapper objectMapper, JSONObject source, Object... args) throws DeserialiserException {
		try {
			return jacksonObjectMapper.readValue(source.toString(), type);
		} catch (IOException ex) {
			throw new DeserialiserException(ex);
		}
	}

}
