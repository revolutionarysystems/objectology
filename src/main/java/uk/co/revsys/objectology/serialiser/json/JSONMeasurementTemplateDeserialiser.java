package uk.co.revsys.objectology.serialiser.json;

import org.json.JSONObject;
import uk.co.revsys.objectology.model.template.MeasurementTemplate;
import uk.co.revsys.objectology.model.template.PropertyTemplate;
import uk.co.revsys.objectology.serialiser.DeserialiserException;
import uk.co.revsys.objectology.serialiser.ObjectMapper;

public class JSONMeasurementTemplateDeserialiser extends JSONAttributeTemplateDeserialiser<MeasurementTemplate>{

	@Override
	public MeasurementTemplate doDeserialiseJSON(MeasurementTemplate object, ObjectMapper objectMapper, JSONObject json, Object... args) throws DeserialiserException {
		return object;
	}

}
