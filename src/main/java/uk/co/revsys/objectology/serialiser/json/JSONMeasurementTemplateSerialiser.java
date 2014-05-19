package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.serialiser.ObjectMapper;
import uk.co.revsys.objectology.serialiser.SerialiserException;

public class JSONMeasurementTemplateSerialiser extends JSONAttributeTemplateSerialiser<MeasurementTemplate>{

	@Override
	public JSONObject doSerialiseJSON(JSONObject json, ObjectMapper objectMapper, MeasurementTemplate object, Object args) throws SerialiserException {
		return json;
	}

}
