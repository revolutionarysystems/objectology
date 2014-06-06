package uk.co.revsys.objectology.serialiser.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class DefaultJSONObjectSerialiser<O extends Object> extends JSONSerialiser<O>{

	private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
	
	@Override
	public Object serialiseJSON(ObjectMapper objectMapper, O object, Object... args) throws SerialiserException {
		try {
			String result = jacksonObjectMapper.writeValueAsString(object);
			return new JSONObject(result);
		} catch (JsonProcessingException ex) {
			throw new SerialiserException(ex);
		}
	}

}
